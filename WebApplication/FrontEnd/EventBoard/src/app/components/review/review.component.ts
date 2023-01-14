import {Component, Input} from '@angular/core';
import {Review} from "../../models/review.model";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {RequestService} from "../../services/request.service";

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent {
  @Input() review: Review

  constructor(protected authService: AuthService, private route: ActivatedRoute, private requestService: RequestService, private router: Router) { }

  onEdit() {

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
