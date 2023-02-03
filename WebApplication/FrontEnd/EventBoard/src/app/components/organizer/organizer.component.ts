import {Component, OnInit} from '@angular/core';
import {EventsService} from "../../services/events.service";
import {Event} from "../../models/event.model";
import {RequestService} from "../../services/request.service";
import {ActivatedRoute} from "@angular/router";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-organizer',
  templateUrl: './organizer.component.html',
  styleUrls: ['./organizer.component.css']
})
export class OrganizerComponent implements OnInit {
  fullName: string
  email: string
  events: Array<Event>

  constructor(protected eventsService: EventsService, private requestService: RequestService, private route: ActivatedRoute, private snackbarService: SnackbarService) { }
  ngOnInit(): void {
    this.events = new Array<Event>()
    const id = this.route.snapshot.params['id']

    if(id)
      this.getOrganizer(id)
    else
      this.snackbarService.openSnackBar("Qualcosa è andato storto, riprova", "OK")
  }

  private getOrganizer(id: number) {
    this.requestService.getOrganizer(id).subscribe({
      next: (response: any) => {
        this.fullName = response.organizer
        this.email = response.email
        for (let element in response.events) {
          let event = {
            id: response.events[element].id,
            title: response.events[element].title,
            date: response.events[element].date,
            urlPoster: response.events[element].urlPoster,
            position: response.events[element].position,
            organizerFullName: response.events[element].organizer.toString(),
          }
          this.events.push(event)
        }
      },
      error: error => {

      }
    })
  }
}
