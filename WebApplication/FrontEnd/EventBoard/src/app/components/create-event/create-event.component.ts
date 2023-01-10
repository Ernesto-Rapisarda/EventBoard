import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RequestService} from "../../services/request.service";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit{
  eventCreateForm: FormGroup


  constructor(private requestService: RequestService, private authService: AuthService) { }

  onSubmit() {
    this.requestService.createEvent(
      this.eventCreateForm.value.date,
      this.eventCreateForm.value.title,
      Number.parseFloat(this.eventCreateForm.value.price),
      false,
      "urlDaCambiareAppenaPossibile",
      this.eventCreateForm.value.description,
      this.eventCreateForm.value.eventType,
      1, //CAMBIARE APPENA POSSIBILE
      this.authService.user.id
    ).subscribe((result: any) => {
      console.log(result)
    })
  }

  ngOnInit(): void {
    this.eventCreateForm = new FormGroup({
        title: new FormControl('', [Validators.required]),
        date: new FormControl(''),
        location: new FormControl('', [Validators.required]),
        // TODO: Bisogna aggiungere un custom validator a price per evitare che venga inserito un input non convertibile (se regex non va)
        price: new FormControl('', [Validators.required, Validators.pattern('^((\\d+)|(\\d+\\.\\d+))$')]),
        eventType: new FormControl('', Validators.required),
        description: new FormControl('')
    }
    )
  }
}
