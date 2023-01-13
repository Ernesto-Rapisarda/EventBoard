import {AfterViewInit, Component, OnInit} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Event} from "../../models/event.model";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit, AfterViewInit {
  event: Event
  liked: boolean
  participate: boolean
  likesNumber: number
  participantsNumber: number
  constructor(private requestService: RequestService, protected authService: AuthService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id']
    this.getEvent(id)
    this.liked = false
    this.participate = false
    this.likesNumber = 0
    this.participantsNumber = 0
  }
  ngAfterViewInit(): void {
    this.liked = this.didILikeThis()
    this.participate = this.willIParticipate()
    this.likesNumber = this.event.likeList.length
    this.participantsNumber = this.event.participationList.length
  }

  getEvent(id: number) {
    this.requestService.getEventById(id).subscribe(
      {
        next: (response: any) => {
          console.log(response)
          this.event = response.event
          this.event.commentList = response.commentList.reverse()           // Reverse so that they'll appear from last to first
          this.event.likeList = response.likeList
          this.event.reviewList = response.reviewList.reverse()             // Reverse so that they'll appear from last to first
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
            if (response === "Success"){
              this.liked ? --this.likesNumber : ++this.likesNumber
              this.liked = !this.liked
            }
          },
          error: error => { this.errorHandler(error.status) }
      })
    }
  }

  onParticipate(){
    if(this.authService.user && this.event){
      this.requestService.doParticipate(this.authService.user.id, this.event.id).subscribe({
        next: (response) => {
          if (response === "Success") {
            this.participate ? --this.participantsNumber : ++this.participantsNumber
            this.participate = !this.participate
          }
        },
        error: error => { this.errorHandler(error.status) }
      })
    }
  }

  onDelete() {
    let choice = confirm("Sei sicuro di voler rimuovere l'evento? Questa operazione è irreversibile.")
    if(choice && this.event){
      this.requestService.deleteEvent(this.event.id).subscribe({
        next: response => {
          alert("L'evento è stato rimosso con successo, ritorno alla pagina principale.")
          this.router.navigateByUrl('')
        },
        error: error => {
          this.errorHandler(error)
        }
      })
    }
  }

  private errorHandler(error: number) {
    switch (error) {
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
}
