import { Component } from '@angular/core';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent {

  /* info */
  poster = 'https://images.unsplash.com/photo-1604311795833-25e1d5c128c6?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8OSUzQTE2fGVufDB8fDB8fA%3D%3D&w=1000&q=80'         // si aspetta un URL di un'immagine con aspect ratio 16:9
  title = 'Titolo';         // max due righe (25 caratteri circa)
  organizer = 'Organizzatore';     // max una riga
  date = new Date(1999, 1, 2);  // basta che sia una data valida che deve ancora arrivare

}
