import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RequestService} from "../../services/request.service";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {ImgbbService} from "../../services/imgbb.service";

@Component({
  selector: 'app-event-edit-dialog',
  templateUrl: './event-edit-dialog.component.html',
  styleUrls: ['./event-edit-dialog.component.css']
})
export class EventEditDialogComponent {
  eventEditDialog: FormGroup;
  urlPoster: string;
  private eventCreateForm: FormGroup<{ date: FormControl<string>; ticketUrl: FormControl<string>; price: FormControl<string>; description: FormControl<string>; location: FormControl<string>; eventType: FormControl<string>; title: FormControl<string> }>;

  constructor(private requestService: RequestService, private authService: AuthService, private router: Router, private imgService: ImgbbService) { }

  eventTypes: string[]

  ngOnInit(): void {
    this.urlPoster = "" // TODO: vanno assegnati quelli già impostati

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

}
