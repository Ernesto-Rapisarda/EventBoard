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

  @ViewChild('autosize') autosize: CdkTextareaAutosize;

  constructor(
    private comuniItaService: ComuniItaService,
    private requestService: RequestService,
    private dialog: MatDialog,
    private imgService: ImgbbService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private _ngZone: NgZone) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id']

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
      description: new FormControl('')
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
        this.eventEditForm.patchValue({
          city: this.event.position.city
        })
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
          console.log(response)
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
      this.imgService.upload(element.files[0]).subscribe({
        next: (response: any) => {
          this.urlPoster = response.data.url
        },
        error: error => {
          this.errorHandler(error)
        }
      })
    }
    else
      alert("ERRORE: La dimensione del file supera i 10MB")
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
      description: this.event.description
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
      if(this.authService.isAdmin())
        message = window.prompt('Inserisci la motivazione')

      this.requestService.editEvent(
        this.eventEditForm.value.date,
        this.eventEditForm.value.title,
        Number.parseFloat(this.eventEditForm.value.price),
        this.eventEditForm.value.soldOut,
        this.urlPoster,
        this.eventEditForm.value.ticketUrl,
        this.eventEditForm.value.description,
        this.eventEditForm.value.eventType,
        location,
        this.event.organizer,
        this.event.id,
        message
      ).subscribe({
        next: response => {
          alert('Evento modificato con successo')
          this.goToEventPage()
        },
        error: error => { this.errorHandler(error) }
      })
    }
  }

  onCancel() {
    this.goToEventPage()
  }

  triggerResize() {
    // Wait for changes to be applied, then trigger textarea resize.
    this._ngZone.onStable.pipe(take(1)).subscribe(() => this.autosize.resizeToFitContent(true));
  }

  private goToEventPage() {
    this.router.navigateByUrl(`/event/${this.event.id}`)
  }

  private errorHandler(error: any) {
    switch (error.status) {
      case 400:
        alert("ERRORE: Errore di elaborazione del server")
        break
      case 403:
        alert("ERRORE: Operazione non autorizzata")
        break;
      case 404:
        alert("ERRORE: Id non trovato")
        break;
      default:
        alert("ERRORE: Errore generico")
    }
  }
}
