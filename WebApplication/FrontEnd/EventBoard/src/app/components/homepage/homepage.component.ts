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
      for(let element in response){
        let event = new Event()
        event.id = response[element].id
        event.title = response[element].title
        event.date = response[element].date
        event.urlPoster = response[element].urlPoster
        event.organizerFullName = response[element].organizerFullName
        this.eventsService.events.push(event)
      }
    })
    console.log(this.eventsService.events)
  }
}
