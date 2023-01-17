import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RequestService} from "../../services/request.service";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {ImgbbService} from "../../services/imgbb.service";

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit{
  eventCreateForm: FormGroup
  urlPoster: string

  constructor(private requestService: RequestService, private authService: AuthService, private router: Router, private imgService: ImgbbService) { }

  eventTypes: string[]

  ngOnInit(): void {
    this.urlPoster = ""
    this.requestService.getEventTypes().subscribe({ next: response => { this.eventTypes = response }})

    this.eventCreateForm = new FormGroup({
      title: new FormControl('', [Validators.required]),
      date: new FormControl(''),
      location: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required, Validators.pattern('^((\\d+)|(\\d+\\.\\d+))$')]),
      eventType: new FormControl('', Validators.required),
      ticketUrl: new FormControl(''),
      description: new FormControl('')
    })
  }

  onSubmit() {
    this.requestService.createEvent(
      this.eventCreateForm.value.date,
      this.eventCreateForm.value.title,
      Number.parseFloat(this.eventCreateForm.value.price),
      false,
      this.urlPoster,
      this.eventCreateForm.value.ticketUrl,
      this.eventCreateForm.value.description,
      this.eventCreateForm.value.eventType,
      1, //CAMBIARE APPENA POSSIBILE
      this.authService.user.id
    ).subscribe({
      next: response => {
        this.router.navigate([''])
      }
    })
  }

  onFileUpload(event: Event) {
    const element = event.currentTarget as HTMLInputElement
    this.imgService.upload(element.files[0]).subscribe({
      next: (response: any) => {
        this.urlPoster = response.data.url
      },
      error: error => { }
    })
  }
}
