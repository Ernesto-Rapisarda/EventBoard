import {Component, OnInit} from '@angular/core';
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

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit{
  eventCreateForm: FormGroup
  urlPoster: string
  longitude: number = 0.0           // Default longitude
  latitude: number = 0.0            // Default latitude
  address: string
  eventTypes: string[]
  regions: string[]
  cities: string[]
  imageUploaded: boolean

  constructor(private dialog: MatDialog, private requestService: RequestService, private authService: AuthService, private router: Router, private imgService: ImgbbService, private comuniItaService: ComuniItaService) { }
  ngOnInit(): void {
    this.urlPoster = ""
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
        this.urlPoster,
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
    // Check if file size exceeds 30MB
    if(element.files[0].size < 30000000) {
      this.imgService.upload(element.files[0]).subscribe({
        next: (response: any) => {
          this.urlPoster = response.data.url
          this.imageUploaded = true
        },
        error: error => { this.errorHandler(error) }
      })
    }
    else
      alert("ERRORE: La dimensione del file supera i 30MB")
  }

  onLocation() {
    let dialogRef = this.dialog.open(LocationChooserDialogComponent, {
      data: {
        longitude: DEFAULT_COORDINATES[0],
        latitude: DEFAULT_COORDINATES[1],
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
}
