import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../auth/auth.service";
import {RequestService} from "../../services/request.service";
import {Event} from "../../models/event.model"
import {EventsService} from "../../services/events.service";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  constructor(protected authService: AuthService, private requestService: RequestService, private eventsService: EventsService) { }

  ngOnInit(): void {
    this.eventsService.events = new Array<Event>()
    this.requestService.getAllEvents().subscribe(response => {
      console.log(response)
      for(let element in response){
        let event = {
          id: response[element].id,
          title: response[element].title,
          date: response[element].date,
          urlPoster: response[element].urlPoster,
          organizerFullName: response[element].organizer.toString(),
        }
        this.eventsService.events.push(event)
      }
    })
    console.log(this.eventsService.events)
  }
}
