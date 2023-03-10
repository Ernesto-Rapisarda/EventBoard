import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../auth/auth.service";
import {RequestService} from "../../services/request.service";
import {EventsService} from "../../services/events.service";
import {MatSlideToggleChange} from "@angular/material/slide-toggle";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  eventTypes: string[]
  selectedEventTypes: string[] = []
  recommendedForYouChecked: boolean = false

  constructor(protected authService: AuthService, private requestService: RequestService, protected eventsService: EventsService) { }

  ngOnInit(): void {
    this.setEventTypes()
    this.setEvents()
  }

  handleSelectedType(eventType: string) {
    if(this.selectedEventTypes.indexOf(eventType) != -1)
      this.removeFromSelectedTypes(eventType)
    else
      this.addToSelectedTypes(eventType)
  }

  addToSelectedTypes(eventType: string) {
    this.selectedEventTypes.push(eventType)
  }

  removeFromSelectedTypes(eventType: string) {
    const index = this.selectedEventTypes.indexOf(eventType)
    if (index !== -1)
      this.selectedEventTypes.splice(index, 1)
  }

  toggleChanges($event: MatSlideToggleChange) {
    this.recommendedForYouChecked = $event.checked;

    // If the user selected the recommended for you toggle, add the user preferences to the selected event types
    if(this.recommendedForYouChecked){
      for(let preference of this.authService.user.preferences)
        this.handleSelectedType(preference.event_type)
    }
    else{
      this.selectedEventTypes = []
    }
  }

  private setEvents() {
    this.eventsService.initializeLists()
    this.requestService.getAllEvents().subscribe({
      next: response => {
        const nowDate = new Date()

        for (let element in response) {
          let event = {
            id: response[element].id,
            title: response[element].title,
            date: response[element].date,
            eventType: response[element].eventType,
            position: response[element].position,
            urlPoster: response[element].urlPoster,
            organizerFullName: response[element].organizer.toString(),
          }

          const eventDate = new Date(event.date)

          // If the event is in the future, add it to the future events array, otherwise add it to the past events array
          if(eventDate > nowDate)
            this.eventsService.futureEvents.push(event)
          else
            this.eventsService.pastEvents.push(event)
        }
      },
      error: error => {

      }
    })
  }

  private setEventTypes() {
    this.requestService.getEventTypes().subscribe({
      next: response => { this.eventTypes = response.sort() },
      error: error => { }
    })
  }
}
