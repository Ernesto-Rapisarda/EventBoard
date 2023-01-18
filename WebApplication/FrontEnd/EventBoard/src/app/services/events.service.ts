import { Injectable } from '@angular/core';
import { Event } from '../models/event.model'

@Injectable({
  providedIn: 'root'
})
export class EventsService {
  events: Array<Event>
  constructor() { }

  sortByDateAsc(): Array<Event> {
    return this.events.sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime())
  }

  sortByDateDesc() {
    return this.events.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime())
  }

  getOnlyFutureEvents(selectedEventTypes: string[]): Array<Event> {
    const nowDate = new Date()
    return this.sortByDateAsc().filter((obj) => {
      const eventDate = new Date(obj.date)
      if(selectedEventTypes.length > 0){
        return (selectedEventTypes.indexOf(obj.eventType) !== -1) && (eventDate.getTime() > nowDate.getTime())
      }
      return eventDate.getTime() > nowDate.getTime()
    })
  }

  getOnlyPastEvents(selectedEventTypes: string[]): Array<Event> {
    const nowDate = new Date()
    return this.sortByDateDesc().filter((obj) => {
      const eventDate = new Date(obj.date)
      if(selectedEventTypes.length > 0){
        console.log(selectedEventTypes)
        console.log(obj.eventType)
        return (selectedEventTypes.indexOf(obj.eventType) !== -1) && (eventDate.getTime() < nowDate.getTime())
      }
      return eventDate.getTime() < nowDate.getTime()
    })
  }
}
