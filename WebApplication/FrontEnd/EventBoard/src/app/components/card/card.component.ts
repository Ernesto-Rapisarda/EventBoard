import {Component, Input} from '@angular/core';
import {EventsService} from "../../services/events.service";
import {Event} from "../../models/event.model";

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent {
  @Input() event: Event

  constructor(protected eventsService: EventsService) { }
}
