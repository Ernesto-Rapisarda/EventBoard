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

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit{
  eventCreateForm: FormGroup
  urlPoster: string
  longitude: number
  latitude: number
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
        this.router.navigate([''])
      },
      error() { }
    })
  }

  onFileUpload(event: Event) {
    const element = event.currentTarget as HTMLInputElement
    this.imgService.upload(element.files[0]).subscribe({
      next: (response: any) => {
        this.urlPoster = response.data.url
        this.imageUploaded = true
      },
      error: error => { }
    })
  }

  private setRegions() {
    this.comuniItaService.getRegions().subscribe({
      next: response => { this.regions = (response as string[]) },
      error: error => { }
    })
  }

  protected setCities() {
    this.comuniItaService.getCities(this.eventCreateForm.value.region).subscribe({
      next: response => { this.cities = (response as string[]) },
      error: error => { }
    })
  }

  private setEventTypes(){
    this.requestService.getEventTypes().subscribe({ next: response => { this.eventTypes = response.sort() }})
  }

  onLocation() {
    let dialogRef = this.dialog.open(LocationChooserDialogComponent, {
      data: {
        latitude: 0.0,
        longitude: 0.0,
        operationConfirmed: false
      }, disableClose: true
    })

    dialogRef.afterClosed().subscribe(result => {
      this.eventCreateForm.patchValue({
        address: result.address
      })
      this.latitude = result.latitude
      this.longitude = result.longitude
      console.log(result)
    })
  }
}
