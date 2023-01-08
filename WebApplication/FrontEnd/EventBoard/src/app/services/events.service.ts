import { Injectable } from '@angular/core';
import { Event } from '../models/event.model'

@Injectable({
  providedIn: 'root'
})
export class EventsService {
  events: Array<Event>
  constructor() { }
}
