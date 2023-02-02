import {Component, Input} from '@angular/core';
import {Comment} from "../../models/comment.model";
import {AuthService} from "../../auth/auth.service";
import {RequestService} from "../../services/request.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {ReportDialogComponent} from "../report-dialog/report-dialog.component";
import {CommentEditDialogComponent} from "../comment-edit-dialog/comment-edit-dialog.component";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent {
  @Input() comment: Comment
  @Input() isOrganizer: boolean

  constructor(protected authService: AuthService, private requestService: RequestService, private router: Router, private route: ActivatedRoute, private dialog: MatDialog , private snackbarService: SnackbarService) {
    // Necessary to enable reloading
    this.router.routeReuseStrategy.shouldReuseRoute = () => {return false;};
  }

  onEdit() {
    let dialogRef = this.dialog.open(CommentEditDialogComponent,{
      data: {
        text: this.comment.message,
        operationConfirmed: false
      }, disableClose: true
    })

    dialogRef.afterClosed().subscribe(result => {
      if(!result.operationConfirmed){
        this.snackbarService.openSnackBar("Operazione annullata.", "OK")
      }
      else{
        const newText = result.text
        let adminMessage = ''
        const isAdmin = this.authService.isAdmin()
        if(isAdmin)
          adminMessage = window.prompt("Qual è il motivo della modifica?")

        if((isAdmin && adminMessage) || !isAdmin){
          this.requestService.editComment(this.comment, newText, adminMessage).subscribe({
            next: response => {
              const eventId = this.route.snapshot.params['id']
              this.snackbarService.openSnackBar("Il commento è stato modificato con successo", "OK")
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
      let choice = confirm("Sei sicuro di voler rimuovere il commento? Questa operazione è irreversibile.")

      if(choice && this.comment) {
        const eventId = this.route.snapshot.params['id']
        this.requestService.deleteComment(this.comment.id, message).subscribe({
          next: response => {
            this.snackbarService.openSnackBar("Il commento è stato rimosso con successo! Ricarico la pagina.", "OK")
            this.router.navigateByUrl(`/event/${eventId}`)
          },
          error: error => { this.errorHandler(error) }
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
        this.requestService.doReportComment(this.comment.id, reason, type).subscribe({
          next: response => {
            this.snackbarService.openSnackBar("Il commento è stato segnalato con successo", "OK")
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
      default:
        this.snackbarService.openSnackBar("ERRORE: Errore sconosciuto", "OK")
        break
    }
  }
}
