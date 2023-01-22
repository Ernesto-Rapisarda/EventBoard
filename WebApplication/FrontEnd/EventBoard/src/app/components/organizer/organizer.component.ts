import {Component, OnInit} from '@angular/core';
import {EventsService} from "../../services/events.service";
import {Event} from "../../models/event.model";
import {RequestService} from "../../services/request.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-organizer',
  templateUrl: './organizer.component.html',
  styleUrls: ['./organizer.component.css']
})
export class OrganizerComponent implements OnInit {
  fullName: string
  email: string
  events: Event[]

  constructor(protected eventsService: EventsService, private requestService: RequestService, private route: ActivatedRoute) { }
  ngOnInit(): void {
    this.eventsService.events = new Array<Event>()
    const id = this.route.snapshot.params['id']

    if(id){
      console.log(id)
      this.requestService.getOrganizer(id).subscribe({
        next: (response: any) => {
          console.log(response)
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
            this.eventsService.events.push(event)
          }
          this.eventsService.sortByDateAsc()
        },
        error: error => {

        }
      })
    }
    else
      alert("Qualcosa è andato storto, riprova")
  }

}
