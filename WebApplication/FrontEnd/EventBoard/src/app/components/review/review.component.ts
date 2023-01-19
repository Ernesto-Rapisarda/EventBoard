import {Component, Input} from '@angular/core';
import {Review} from "../../models/review.model";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {RequestService} from "../../services/request.service";
import {ReportDialogComponent} from "../report-dialog/report-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ReviewEditDialogComponent} from "../review-edit-dialog/review-edit-dialog.component";

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent {
  @Input() review: Review

  constructor(protected authService: AuthService, private route: ActivatedRoute, private requestService: RequestService, private router: Router, private dialog: MatDialog) { }

  onEdit() {
    let dialogRef = this.dialog.open(ReviewEditDialogComponent,{
      data: {
        text: this.review.message,
        rating: this.review.rating,
        operationConfirmed: false
      }, disableClose: true
    })

    dialogRef.afterClosed().subscribe(result => {
      if(!result.operationConfirmed){
        alert("Operazione annullata")
      }
      else{
        const newText = result.text
        const newRating = result.rating
        let adminMessage = ''
        if(this.authService.isAdmin())
          adminMessage = window.prompt("Qual è il motivo della rimozione?")
        this.requestService.editReview(this.review, newText, newRating, adminMessage).subscribe({
          next: response => {
            const eventId = this.route.snapshot.params['id']
            alert("La recensione è stata modificata con successo")
            this.router.navigateByUrl(`/event/${eventId}`)
          },
          error: error => {
            this.errorHandler(error)
          }
        })
      }
    })
  }

  onDelete() {
    let choice = confirm("Sei sicuro di voler rimuovere la recensione? Questa operazione è irreversibile.")
    if(choice && this.review) {
      const eventId = this.route.snapshot.params['id']
      this.requestService.deleteReview(this.review.event, this.review.person).subscribe({
        next: response => {
          alert("La recensione è stata rimosso con successo! Ricarico la pagina.")
          this.router.navigateByUrl(`/event/${eventId}`)
        },
        error: error => { this.errorHandler(error) }
      })
    }
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
        alert("Operazione annullata")
      }
      else{
        const type = result.type
        const reason = result.reason
        this.requestService.doReportReview(this.review.event, this.review.person, type, reason).subscribe({
          next: response => {
            alert("La recensione è stata segnalata con successo")
          },
          error: error => {
            this.errorHandler(error)
          }
        })
      }
    })
  }

  private errorHandler(error: number) {
    switch (error) {
      case 400:
        alert("ERRORE: Token non corrispondende all'id utente o all'admin")
        break
      case 403:
        alert("ERRORE: Utente non autorizzato")
        break
      case 404:
        alert("ERRORE: Commento non trovato")
        break
    }
  }
}
