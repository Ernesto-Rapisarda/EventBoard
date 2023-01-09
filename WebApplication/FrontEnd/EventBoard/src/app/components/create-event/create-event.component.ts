import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit{
  eventCreateForm: FormGroup


  onSubmit() {
    console.log(this.eventCreateForm)
  }

  ngOnInit(): void {
    this.eventCreateForm = new FormGroup({
        title: new FormControl('', [Validators.required]),
        date: new FormControl(''),
        location: new FormControl('', [Validators.required]),
        price: new FormControl('', [Validators.required]),
        eventType: new FormControl(''),
        description: new FormControl('')
    }
    )
  }
}
