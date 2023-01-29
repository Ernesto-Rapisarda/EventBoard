import {Injectable} from '@angular/core';
import {Event} from '../models/event.model'

@Injectable({
  providedIn: 'root'
})
export class EventsService {
  pastEvents: Array<Event>
  futureEvents: Array<Event>

  constructor() { }

  getOnlyFutureEvents(selectedEventTypes: string[]): Array<Event> {
    if(this.futureEvents.length > 0){
      return this.futureEvents.filter((obj) => {
        if(selectedEventTypes.length > 0){
          return (selectedEventTypes.indexOf(obj.eventType) !== -1)
        }
        return true
      })
    }
    return []
  }

  getOnlyPastEvents(selectedEventTypes: string[]): Array<Event> {
    if(this.pastEvents.length > 0){
      return this.pastEvents.filter((obj) => {
        if(selectedEventTypes.length > 0){
          return (selectedEventTypes.indexOf(obj.eventType) !== -1)
        }
        return true
      }).reverse()            // Reversed because we want to show the most recent events first
    }
    return []
  }

  initializeLists() {
    this.pastEvents = new Array<Event>()
    this.futureEvents = new Array<Event>()
  }
}
