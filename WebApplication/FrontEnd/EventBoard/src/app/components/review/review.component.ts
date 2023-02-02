import {Component, Input} from '@angular/core';
import {Review} from "../../models/review.model";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {RequestService} from "../../services/request.service";
import {ReportDialogComponent} from "../report-dialog/report-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ReviewEditDialogComponent} from "../review-edit-dialog/review-edit-dialog.component";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent {
  @Input() review: Review

  constructor(protected authService: AuthService, private route: ActivatedRoute, private requestService: RequestService, private router: Router, private dialog: MatDialog, private snackbarService: SnackbarService) { }

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
        this.snackbarService.openSnackBar("Operazione annullata", "OK")
      }
      else{
        const newText = result.text
        const newRating = result.rating
        let adminMessage = ''
        const isAdmin = this.authService.isAdmin()
        if(isAdmin)
          adminMessage = window.prompt("Qual è il motivo della rimozione?")

        if((isAdmin && adminMessage) || !isAdmin){
          this.requestService.editReview(this.review, newText, newRating, adminMessage).subscribe({
            next: response => {
              const eventId = this.route.snapshot.params['id']
              this.snackbarService.openSnackBar("La recensione è stata modificata con successo", "OK")
              this.router.navigateByUrl(`/event/${eventId}`)
            },
            error: error => {
              this.errorHandler(error)
            }
          })
        }
        else {
          this.snackbarService.openSnackBar("Operazione annullata", "OK")
        }
      }
    })
  }

  onDelete() {
    let message = ''
    const isAdmin = this.authService.isAdmin()
    if(isAdmin)
      message = window.prompt("Qual è il motivo della rimozione?")

    if((isAdmin && message) || !isAdmin){
      let choice = confirm("Sei sicuro di voler rimuovere la recensione? Questa operazione è irreversibile.")

      if(choice && this.review) {
        const eventId = this.route.snapshot.params['id']
        this.requestService.deleteReview(this.review.event, this.review.person, message).subscribe({
          next: response => {
            this.snackbarService.openSnackBar("La recensione è stata rimossa con successo! Ricarico la pagina.", "OK")
            this.router.navigateByUrl(`/event/${eventId}`)
          },
          error: error => { this.errorHandler(error) }
        })
      }
      else {
        this.snackbarService.openSnackBar("Operazione annullata.", "OK")
      }
    }
    else{
      this.snackbarService.openSnackBar("Operazione annullata.", "OK")
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
        this.snackbarService.openSnackBar("Operazione annullata", "OK")
      }
      else{
        const type = result.type
        const reason = result.reason
        this.requestService.doReportReview(this.review.event, this.review.person, type, reason).subscribe({
          next: response => {
            this.snackbarService.openSnackBar("La recensione è stata segnalata con successo", "OK")
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
        this.snackbarService.openSnackBar("ERRORE: Token non corrispondende all'id utente o all'admin", "OK")
        break
      case 403:
        this.snackbarService.openSnackBar("ERRORE: Utente non autorizzato", "OK")
        break
      case 404:
        this.snackbarService.openSnackBar("ERRORE: Commento non trovato", "OK")
        break
    }
  }
}
