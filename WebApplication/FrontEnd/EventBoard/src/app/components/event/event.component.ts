import {AfterViewInit, Component, OnInit} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute} from "@angular/router";
import {Event} from "../../models/event.model";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit, AfterViewInit {
  event: Event
  liked: boolean
  participate: boolean
  constructor(private requestService: RequestService, protected authService: AuthService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id']
    this.getEvent(id)
    this.liked = false
    this.participate = false
  }
  ngAfterViewInit(): void {
    this.liked = this.didILikeThis()
    this.participate = this.willIParticipate()
  }

  getEvent(id: number) {
    this.requestService.getEventById(id).subscribe(
      {
        next: (response: any) => {
          this.event = response.event
          this.event.commentList = response.commentList
          this.event.likeList = response.likeList
          this.event.reviewList = response.reviewList
          this.event.participationList = response.partecipationList
          this.event.organizerFullName = response.organizerFullName
        }
      })
  }

  // returns true if the user liked this event
  didILikeThis() {
    for(let like of this.event.likeList){
      if(like.person == this.authService.user.id)
        return true
    }
    return false
  }

  // returns true if the user liked this event
  willIParticipate() {
    for(let participation of this.event.participationList){
      if(participation.person == this.authService.user.id)
        return true
    }
    return false
  }

  onLike() {
    if(this.authService.user && this.event){
      this.requestService.doLike(this.authService.user.id, this.event.id).subscribe({
          next: response => {
            if (response === "Success")
              this.liked = !this.liked
          }
          , error: error => {
            switch (error.status) {
              case 400:
                alert("ERRORE: Token non corrispondende all'id utente")
                break
              case 403:
                alert("ERRORE: Utente non autorizzato")
                break
              case 404:
                alert("ERRORE: Evento non trovato")
                break
            }
          }
      })
    }
  }

  onParticipate(){
    if(this.authService.user && this.event){
      this.requestService.doParticipate(this.authService.user.id, this.event.id).subscribe({
        next: (response) => {
          if (response === "Success")
            this.participate = !this.participate
        },
        error: (error) => {
          switch (error.status) {
            case 400:
              alert("ERRORE: Token non corrispondende all'id utente")
              break
            case 403:
              alert("ERRORE: Utente non autorizzato")
              break
            case 404:
              alert("ERRORE: Evento non trovato")
              break
          }
        }
      })
    }
  }
}
