import {Component, OnInit} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Event} from "../../models/event.model";
import {MatDialog} from "@angular/material/dialog";
import {ReportDialogComponent} from "../report-dialog/report-dialog.component";
import {EventImageDialogComponent} from "../event-image-dialog/event-image-dialog.component";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {
  event: Event
  liked: boolean
  participate: boolean
  likesNumber: number
  participantsNumber: number
  interactionsNumber: number
  averageRating: number
  nowDate: Date
  constructor(private requestService: RequestService, protected authService: AuthService, private route: ActivatedRoute, private router: Router, private dialog: MatDialog, private snackbarService: SnackbarService) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id']
    this.getEvent(id)
    this.liked = false
    this.participate = false
    this.likesNumber = 0
    this.participantsNumber = 0
    this.nowDate = new Date()
    window.scroll({top: 0})   // Scroll to top of the page when the component is loaded
  }

  getEvent(id: number) {
    this.requestService.getEventById(id).subscribe(
      {
        next: (response: any) => {
          // Event setup
          this.event = response.event
          this.event.description = this.event.description.replace(/\n/g, '<br>')
          this.event.position = response.position
          this.event.commentList = response.commentList.reverse()           // Reverse so that they'll appear from last to first
          this.event.likeList = response.likeList
          this.event.reviewList = response.reviewList.reverse()             // Reverse so that they'll appear from last to first
          this.event.participationList = response.partecipationList
          this.event.organizerFullName = response.organizerFullName

          // Page necessary data setup

          // If the user is logged in, check if he likes/participate this event
          if(this.authService.user){
            this.liked = this.didILikeThis()
            this.participate = this.willIParticipate()
          }

          let ratingsSum = 0
          for(let review of this.event.reviewList)
            ratingsSum += review.rating

          if(this.event.reviewList.length > 0)
            this.averageRating = ratingsSum / this.event.reviewList.length

          this.likesNumber = this.event.likeList.length
          this.participantsNumber = this.event.participationList.length
          this.interactionsNumber = (this.event.commentList.length)+(this.event.reviewList.length)
        }
      })
  }

  // returns true if the user likes this event
  didILikeThis() {
    for(let like of this.event.likeList){
      if(like.person == this.authService.user.id)
        return true
    }
    return false
  }

  // returns true if the user "participates" this event
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

  onEdit() {
    this.router.navigateByUrl(`/event/edit/${this.event.id}`)
  }


  onDelete() {
    let message = ''
    const isAdmin = this.authService.isAdmin()
    if(isAdmin)
      message = window.prompt("Qual è il motivo della rimozione?")

    if((isAdmin && message) || !isAdmin){
      let choice = confirm("Sei sicuro di voler rimuovere l'evento? Questa operazione è irreversibile.")

      if(choice && this.event){
        this.requestService.deleteEvent(this.event.id, message).subscribe({
          next: response => {
            this.snackbarService.openSnackBar("L'evento è stato rimosso con successo.", "OK")
            this.router.navigateByUrl('')
          },
          error: error => {
            this.errorHandler(error)
          }
        })
      }
      else{
        this.snackbarService.openSnackBar("Operazione annullata.", "OK")
      }
    }
    else {
      this.snackbarService.openSnackBar("Operazione annullata.", "OK")
    }

  }

  onTicketPage() {
    // Check whether the ticket url has http or https at the beginning
    if(!this.event.urlTicket.startsWith("http://") && !this.event.urlTicket.startsWith("https://"))
      this.event.urlTicket = "http://" + this.event.urlTicket

    window.open(this.event.urlTicket, "_blank")
  }

  onImgClick() {
    let dialogRef = this.dialog.open(EventImageDialogComponent, {
      data: {
        image: this.event.urlPoster
      }, maxHeight: '90vh', maxWidth: '90vw'
    })
  }

  onReport() {
    let dialogRef = this.dialog.open(ReportDialogComponent,{
      data: {
        type: '',
        reason: '',
        operationConfirmed: false
      }, disableClose: true
    })

    dialogRef.afterClosed().subscribe(result => {
      if(!result.operationConfirmed){
        this.snackbarService.openSnackBar("Operazione annullata", "OK")
      }
      else{
        const type = result.type
        const reason = result.reason
        this.requestService.doReportEvent(this.event.id, reason, type).subscribe({
          next: response => {
            this.snackbarService.openSnackBar("L'evento è stato segnalato con successo", "OK")
          },
          error: error => {
            this.errorHandler(error)
          }
        })
      }
    })
  }

  isAPastEvent() : boolean{
    const eventDate = new Date(this.event.date)
    return eventDate < this.nowDate
  }

  private errorHandler(error: number) {
    switch (error) {
      case 400:
        this.snackbarService.openSnackBar("ERRORE: Token non corrispondende all'id utente", "OK")
        break
      case 403:
        this.snackbarService.openSnackBar("ERRORE: Utente non autorizzato", "OK")
        break
      case 404:
        this.snackbarService.openSnackBar("ERRORE: Evento non trovato", "OK")
        break
    }
  }
}
