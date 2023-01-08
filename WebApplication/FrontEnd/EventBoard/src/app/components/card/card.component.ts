import { Component } from '@angular/core';
import {EventsService} from "../../services/events.service";

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent {

  /* info */
  poster = 'https://images.unsplash.com/photo-1604311795833-25e1d5c128c6?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8OSUzQTE2fGVufDB8fDB8fA%3D%3D&w=1000&q=80'         // si aspetta un URL di un'immagine con aspect ratio 16:9
  title: string;         // max due righe (25 caratteri circa)
  organizer: string;     // max una riga
  date: Date  // basta che sia una data valida che deve ancora arrivare

  constructor(protected eventsService: EventsService) { }

}
