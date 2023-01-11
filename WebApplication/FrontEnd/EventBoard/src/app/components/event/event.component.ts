import {Component, OnInit} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute} from "@angular/router";
import {Event} from "../../models/event.model";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {
  event: Event

  constructor(private requestService: RequestService, private authService: AuthService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id']
    console.log(id)
    this.getEvent(id)
  }

  getEvent(id: number) {
    this.requestService.getEventById(id).subscribe((response: any) => {
      console.log(response)
      this.event = response.event
      this.event.commentList = response.commentList
      this.event.reviewList = response.event.reviewList
      this.event.participationList = response.event.partecipationList
      this.event.organizerFullName = response.organizerFullName
    })
  }
}
