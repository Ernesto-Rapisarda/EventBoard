import {Component, NgZone, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RequestService} from "../../services/request.service";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {ImgbbService} from "../../services/imgbb.service";
import {ComuniItaService} from "../../services/comuni-ita.service";
import {Location} from "../../models/location.model";
import {MatDialog} from "@angular/material/dialog";
import {LocationChooserDialogComponent} from "../location-chooser-dialog/location-chooser-dialog.component";
import {DEFAULT_COORDINATES} from "../../../constants";
import {take} from "rxjs/operators";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {ImgurService} from "../../services/imgur.service";
import {ThumbsnapService} from "../../services/thumbsnap.service";
import {MapboxService} from "../../services/mapbox.service";

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit{
  eventCreateForm: FormGroup
  longitude: number = 0.0           // Default longitude
  latitude: number = 0.0            // Default latitude
  address: string
  eventTypes: string[]
  regions: string[]
  cities: string[]
  imageUploaded: boolean
  @ViewChild('autosize') autosize: CdkTextareaAutosize;

  constructor(private dialog: MatDialog, private requestService: RequestService, private authService: AuthService, private router: Router, private imgurService: ImgurService, private imgbbService: ImgbbService, private thumbsnapService: ThumbsnapService, private comuniItaService: ComuniItaService, private mapboxService: MapboxService, private _ngZone: NgZone) { }
  ngOnInit(): void {
    this.imageUploaded = false
    this.setEventTypes()
    this.setRegions()

    this.eventCreateForm = new FormGroup({
      title: new FormControl('', [Validators.required]),
      date: new FormControl('', [Validators.required]),
      region: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required, Validators.pattern('^((\\d+)|(\\d+\\.\\d+))$')]),
      poster: new FormControl('', [Validators.required]),
      eventType: new FormControl('', Validators.required),
      ticketUrl: new FormControl(''),
      description: new FormControl('')
    })
  }

  onSubmit() {
    const location: Location = {
      id: null,
      region: this.eventCreateForm.value.region,
      city: this.eventCreateForm.value.city,
      address: this.eventCreateForm.value.address,
      longitude: this.longitude,
      latitude: this.latitude
    }
    const confirmString = this.getConfirmString()
    if(confirm("Vuoi creare un evento con i seguenti dati?\n" + confirmString)) {
      this.requestService.createEvent(
        this.eventCreateForm.value.date,
        this.eventCreateForm.value.title,
        Number.parseFloat(this.eventCreateForm.value.price),
        false,                                        // Event can't be sold out when created
        this.eventCreateForm.value.poster,
        this.eventCreateForm.value.ticketUrl,
        this.eventCreateForm.value.description,
        this.eventCreateForm.value.eventType,
        location,
        this.authService.user.id
      ).subscribe({
        next: response => {
          alert("Evento creato con successo, ritorno alla homepage")
          this.router.navigate([''])
        },
        error: error => { this.errorHandler(error) }
      })
    }
  }

  onFileUpload(event: Event) {
    const element = event.currentTarget as HTMLInputElement
    // Check if file size exceeds 10MB
    if(element.files[0].size < 10000000) {
      this.imgbbUpload(element.files[0])
    }
    else
      alert("ERRORE: La dimensione del file supera i 10MB")
  }

  onLocation() {
    let longitude = DEFAULT_COORDINATES[0]
    let latitude = DEFAULT_COORDINATES[1]
    if(this.eventCreateForm.value.region !== '' && this.eventCreateForm.value.city !== '') {
      this.mapboxService.getForwardGeocode(this.eventCreateForm.value.region, this.eventCreateForm.value.city, (this.eventCreateForm.value.address || '')).subscribe({
        next: (response: any) => {
          console.log(response)
          longitude = response.features[0].center[0]
          latitude = response.features[0].center[1]

          let dialogRef = this.dialog.open(LocationChooserDialogComponent, {
            data: {
              longitude: longitude,
              latitude: latitude,
              operationConfirmed: false
            }, disableClose: true
          })

          dialogRef.afterClosed().subscribe(result => {
            this.eventCreateForm.patchValue({
              address: result.address
            })
            this.latitude = result.latitude
            this.longitude = result.longitude
          })
        }
      })
    }
  }

  /** DO NOT ABSOLUTELY REMOVE, IT SEEMS UNUSED BUT IT IS USED BY TEXTAREAs FOR RESIZING*/
  triggerResize() {
    // Wait for changes to be applied, then trigger textarea resize.
    this._ngZone.onStable.pipe(take(1)).subscribe(() => this.autosize.resizeToFitContent(true));
  }

  // SERVICE FUNCTIONS
  private setRegions() {
    this.comuniItaService.getRegions().subscribe({
      next: response => { this.regions = (response as string[]) },
      error: error => { this.errorHandler(error) }
    })
  }

  protected setCities() {
    this.comuniItaService.getCities(this.eventCreateForm.value.region).subscribe({
      next: response => { this.cities = (response as string[]) },
      error: error => { this.errorHandler(error) }
    })
  }

  private setEventTypes(){
    this.requestService.getEventTypes().subscribe({ next: response => { this.eventTypes = response.sort() }})
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

  private getConfirmString() {
    const str = "Titolo: " + this.eventCreateForm.value.title + "\n" +
      "Data: " + this.eventCreateForm.value.date + "\n" +
      "Regione: " + this.eventCreateForm.value.region + "\n" +
      "Città: " + this.eventCreateForm.value.city + "\n" +
      "Indirizzo: " + this.eventCreateForm.value.address + "\n" +
      "Prezzo: " + this.eventCreateForm.value.price + "\n" +
      "Tipo di evento: " + this.eventCreateForm.value.eventType + "\n" +
      "URL biglietti: " + this.eventCreateForm.value.ticketUrl + "\n" +
      "Descrizione: " + this.eventCreateForm.value.description + "\n"
    return str
  }

  private imgbbUpload(b64Image: any){
    let posterUrl: string
    this.imgbbService.upload(b64Image).subscribe({
      next: (response: any) => {
        posterUrl = response.data.url
        this.patchPosterValue(posterUrl)
      },
      error: error => {
        posterUrl = '#'
        alert("ERRORE: Impossibile caricare l'immagine, verrà utilizzata quella di default")
        this.patchPosterValue(posterUrl)
      },
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
        posterUrl = '#'
        alert("ERRORE: Impossibile caricare l'immagine, verrà utilizzata quella di default")
        this.patchPosterValue(posterUrl)
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
        posterUrl = '#'
        alert("ERRORE: Impossibile caricare l'immagine, verrà utilizzata quella di default")
        this.patchPosterValue(posterUrl)
      }
    })
  }

  private patchPosterValue(posterUrl: string){
    this.eventCreateForm.patchValue({
      poster: posterUrl
    })
    this.imageUploaded = true
  }
}
