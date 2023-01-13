import { Component } from '@angular/core';
import {EventsService} from "../../services/events.service";

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent {
  /* info */
  constructor(protected eventsService: EventsService) { }
}
