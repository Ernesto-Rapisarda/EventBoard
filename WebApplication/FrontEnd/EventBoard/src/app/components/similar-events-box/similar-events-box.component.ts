import {Component, Input, OnInit} from '@angular/core';
import {Event} from "../../models/event.model";
import {RequestService} from "../../services/request.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-similar-events-box',
  templateUrl: './similar-events-box.component.html',
  styleUrls: ['./similar-events-box.component.css']
})
export class SimilarEventsBoxComponent implements OnInit {

  @Input() parentEvent: Event
  similarEvents: Event[]

  constructor(private requestService: RequestService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.similarEvents = new Array<Event>()

    if(this.parentEvent) {
      const type = this.parentEvent.eventType
      this.requestService.getFilteredEvents(type).subscribe({
        next: (response: any) => {
          // Take the first 4 events
          this.similarEvents = response.splice(0, 4)
        },
        error: (error: any) => {
        }
      })
    }
  }
}

