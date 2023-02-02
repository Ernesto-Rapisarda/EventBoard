import {Component, OnInit} from '@angular/core';
import {Event} from "../../models/event.model";
import {RequestService} from "../../services/request.service";
import {EventsService} from "../../services/events.service";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-search-title',
  templateUrl: './search-title.component.html',
  styleUrls: ['./search-title.component.css']
})
export class SearchTitleComponent implements OnInit {
  searchPrompt: string;
  searchDone: boolean

  events: Array<Event>

  constructor(private eventsService: EventsService, private requestService: RequestService, private snackbarService: SnackbarService) { }

  ngOnInit(): void {
    this.events = []
    this.searchDone = false
    this.searchPrompt = ''
  }

  onSearch() {
    this.searchDone = true
    this.events = []
    if(this.searchPrompt !== ''){
      this.requestService.searchEvents('title', null, null, null, null, this.searchPrompt).subscribe({
        next: response => {
          for(let element in response){
            let event: Event = {
              id: response[element].id,
              title: response[element].title,
              date: response[element].date,
              eventType: response[element].eventType,
              position: response[element].position,
              urlPoster: response[element].urlPoster,
              organizerFullName: response[element].organizer.toString(),
            }
            this.events.push(event)
          }
        },
        error: error => {
          this.errorHandler(error)
        }
      })
    }
  }

  private errorHandler(error: any) {
    switch(error.status) {
      case 400:
        this.snackbarService.openSnackBar("ERRORE: Operazione non valida", "OK")
        break
      case 403:
        this.snackbarService.openSnackBar("ERRORE: Non hai i permessi per eseguire questa operazione", "OK")
        break
      case 404:
        this.snackbarService.openSnackBar("ERRORE: Nessun evento trovato", "OK")
    }
  }
}
