import {Component, NgZone, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RequestService} from "../../services/request.service";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ImgbbService} from "../../services/imgbb.service";
import {MatDialog} from "@angular/material/dialog";
import {Event as MyEvent} from "../../models/event.model";
import {ComuniItaService} from "../../services/comuni-ita.service";
import {LocationChooserDialogComponent} from "../location-chooser-dialog/location-chooser-dialog.component";
import {Location} from "../../models/location.model";
import {take} from "rxjs/operators";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {ImgurService} from "../../services/imgur.service";
import {ThumbsnapService} from "../../services/thumbsnap.service";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-event-edit',
  templateUrl: './event-edit.component.html',
  styleUrls: ['./event-edit.component.css']
})

export class EventEditComponent implements OnInit {
  eventEditForm!: FormGroup
  urlPoster: string
  event: MyEvent
  eventTypes: string[]
  regions: string[]
  cities: string[]
  latitude: number
  longitude: number
  isUploading: boolean

  myCityInitialized: boolean;
  @ViewChild('autosize') autosize: CdkTextareaAutosize;

  constructor(
    private comuniItaService: ComuniItaService,
    private requestService: RequestService,
    private dialog: MatDialog,
    private imgbbService: ImgbbService,
    private imgurService: ImgurService,
    private thumbsnapService: ThumbsnapService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private snackbarService: SnackbarService,
    private _ngZone: NgZone) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id']
    this.isUploading = false
    this.myCityInitialized = false

    this.initializeForm()
    this.getEvent(id)
    this.setRegions()
  }

  private initializeForm() {
    this.eventEditForm = new FormGroup({
      title: new FormControl('', [Validators.required]),
      date: new FormControl(''),
      region: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      soldOut: new FormControl(''),
      price: new FormControl('', [Validators.required, Validators.pattern('^((\\d+)|(\\d+\\.\\d+))$')]),
      eventType: new FormControl('', Validators.required),
      ticketUrl: new FormControl(''),
      description: new FormControl(''),
      poster: new FormControl('')
    })
  }

  private setRegions() {
    this.comuniItaService.getRegions().subscribe({
      next: response => {
        this.regions = (response as string[])
        if(this.event.position.region != ""){
          this.eventEditForm.patchValue({
            region: this.event.position.region
          })
          this.setCities()
        }
      },
      error: error => { this.errorHandler(error) }
    })
  }

  setCities() {
    this.comuniItaService.getCities(this.eventEditForm.value.region).subscribe({
      next: response => {
        this.cities = (response as string[])
        if(!this.myCityInitialized){
          this.eventEditForm.patchValue({
            city: this.event.position.city
          })
          this.myCityInitialized = true
        }
        else {
          this.eventEditForm.patchValue({
            city: this.cities[0]
          })
        }
      },
      error: error => { this.errorHandler(error) }
    })
  }

  private setEventTypes(){
    this.requestService.getEventTypes().subscribe({ next: response => {
      this.eventTypes = response.sort()
        this.eventEditForm.patchValue({
          eventType: this.event.eventType
        })
    }})
  }

  onLocation() {
    let dialogRef = this.dialog.open(LocationChooserDialogComponent, {
      data: {
        latitude: this.event.position.latitude,
        longitude: this.event.position.longitude,
        operationConfirmed: false
      }, disableClose: true
    })

    dialogRef.afterClosed().subscribe(result => {
      if(result.operationConfirmed){
        this.eventEditForm.patchValue({
          address: result.address
        })
        this.latitude = result.latitude
        this.longitude = result.longitude
      }
    })
  }

  getEvent(id: number) {
    this.requestService.getEventById(id).subscribe(
      {
        next: (response: any) => {
          this.event = response.event
          this.setEventTypes()                      // Once I have the event, I can set the event types (and the event type)
          this.event.position = response.position
          this.latitude = this.event.position.latitude
          this.longitude = this.event.position.longitude
          this.urlPoster = this.event.urlPoster
          this.patchValues()                        // Once I gathered all the data, I can patch the form with the missing values
        }
      })
  }

  onFileUpload(event: Event) {
    const element = event.currentTarget as HTMLInputElement

    // Check if file size exceeds 10MB
    if(element.files[0].size < 10000000) {
      this.isUploading = true
      this.imgbbUpload(element.files[0])
    }
    else
      this.snackbarService.openSnackBar("ERRORE: La dimensione del file supera i 10MB", "OK")
  }

  private patchValues(){
    const date = new Date(this.event.date)

    this.eventEditForm.patchValue({
      title: this.event.title,
      date: date,
      address: this.event.position.address,
      soldOut: this.event.soldOut,
      price: this.event.price,
      ticketUrl: this.event.urlTicket,
      description: this.event.description,
      poster: this.event.urlPoster
    })
  }

  onConfirm() {
    if(this.authService.user){
      const location: Location = {
        id: this.event.position.id,
        region: this.eventEditForm.value.region,
        city: this.eventEditForm.value.city,
        address: this.eventEditForm.value.address,
        longitude: this.longitude,
        latitude: this.latitude
      }

      let message = ''
      const isAdmin = this.authService.isAdmin()
      if(isAdmin)
        message = window.prompt('Inserisci la motivazione')

      if((isAdmin && message) || !isAdmin){
        this.requestService.editEvent(
          this.eventEditForm.value.date,
          this.eventEditForm.value.title,
          Number.parseFloat(this.eventEditForm.value.price),
          this.eventEditForm.value.soldOut,
          this.eventEditForm.value.poster,
          this.eventEditForm.value.ticketUrl,
          this.eventEditForm.value.description,
          this.eventEditForm.value.eventType,
          location,
          this.event.organizer,
          this.event.id,
          message
        ).subscribe({
          next: response => {
            this.snackbarService.openSnackBar('Evento modificato con successo', "OK")
            this.goToEventPage()
          },
          error: error => { this.errorHandler(error) }
        })
      }
      else {
        this.snackbarService.openSnackBar("Operazione annullata", "OK")
      }
    }
  }

  onCancel() {
    this.goToEventPage()
  }

  /** DO NOT ABSOLUTELY REMOVE, IT SEEMS UNUSED BUT IT IS USED BY TEXTAREAs FOR RESIZING*/
  triggerResize() {
    // Wait for changes to be applied, then trigger textarea resize.
    this._ngZone.onStable.pipe(take(1)).subscribe(() => this.autosize.resizeToFitContent(true));
  }

  private goToEventPage() {
    this.router.navigateByUrl(`/event/${this.event.id}`)
  }

  private imgbbUpload(b64Image: any){
    let posterUrl: string
    this.imgbbService.upload(b64Image).subscribe({
      next: (response: any) => {
        posterUrl = response.data.url
        this.patchPosterValue(posterUrl)
      },
      error: error => {
        this.snackbarService.openSnackBar("ERRORE: Impossibile caricare l'immagine, la locandina rimarrà invariata.\nTi consigliamo di riprovare più tardi", "OK")
      }
    })
  }

  /** To be used if ImgBB/Thumbsnap service is down */
  private imgurUpload(b64Image: any) {
    let posterUrl: string
    this.imgurService.upload(b64Image).subscribe({
      next: (response: any) => {
        posterUrl = response.data.link
        this.patchPosterValue(posterUrl)
      },
      error: error => {
        this.snackbarService.openSnackBar("ERRORE: Impossibile caricare l'immagine, la locandina rimarrà invariata.\nTi consigliamo di riprovare più tardi", "OK")
      }
    })
  }

  /** To be used if ImgBB/ImgUR service is down */
  private thumbsnapUpload(b64Image: any) {
    let posterUrl: string
    this.thumbsnapService.upload(b64Image).subscribe({
      next: (response: any) => {
        posterUrl = response.data.media
        this.patchPosterValue(posterUrl)
      },
      error: error => {
        this.snackbarService.openSnackBar("ERRORE: Impossibile caricare l'immagine, la locandina rimarrà invariata.\nTi consigliamo di riprovare più tardi", "OK")
      }
    })
  }

  private patchPosterValue(posterUrl: string){
    this.eventEditForm.patchValue({
      poster: posterUrl
    })
    this.isUploading = false
  }

  private errorHandler(error: any) {
    switch (error.status) {
      case 400:
        this.snackbarService.openSnackBar("ERRORE: Errore di elaborazione del server", "OK")
        break
      case 403:
        this.snackbarService.openSnackBar("ERRORE: Operazione non autorizzata", "OK")
        break;
      case 404:
        this.snackbarService.openSnackBar("ERRORE: Id non trovato", "OK")
        break;
      default:
        this.snackbarService.openSnackBar("ERRORE: Errore generico", "OK")
    }
  }
}
