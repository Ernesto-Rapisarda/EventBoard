import {Component, Input} from '@angular/core';
import {Comment} from "../../models/comment.model";
import {AuthService} from "../../auth/auth.service";
import {RequestService} from "../../services/request.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent {
  @Input() comment: Comment

  constructor(protected authService: AuthService, private requestService: RequestService, private router: Router, private route: ActivatedRoute) {
    // Necessary to enable reloading
    this.router.routeReuseStrategy.shouldReuseRoute = () => {return false;};
  }

  onEdit() {

  }

  onDelete() {
    let choice = confirm("Sei sicuro di voler rimuovere il commento? Questa operazione è irreversibile.")
    if(choice && this.comment) {
      const eventId = this.route.snapshot.params['id']
      this.requestService.deleteComment(this.comment.id).subscribe({
        next: response => {
          alert("Il commento è stato rimosso con successo! Ricarico la pagina.")
          this.router.navigateByUrl(`/event/${eventId}`)
        },
        error: error => { this.errorHandler(error) }
      })
    }
  }

  onReport() {

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
        alert("ERRORE: Commento non trovato")
        break
    }
  }
}
