import {Component, NgZone, ViewChild} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {take} from "rxjs/operators";

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.css']
})
export class CommentFormComponent {
  text: string = ''
  @ViewChild('autosize') autosize: CdkTextareaAutosize;
  constructor(private requestService: RequestService, private route: ActivatedRoute, private authService: AuthService, private router: Router, private _ngZone: NgZone) {
    // Necessary to enable reloading
    this.router.routeReuseStrategy.shouldReuseRoute = () => {return false;};
  }
  onSend() {
    if(this.text !== ''){
      const eventId = this.route.snapshot.params['id']
      const userId = this.authService.user.id
      this.requestService.addComment(this.text, eventId, userId).subscribe({
        next: response => { this.router.navigateByUrl(`/event/${eventId}`) },
        error: error => { this.errorHandler(error) }
      })
    }
    else
      alert("ERRORE: Testo del commento vuoto!")
  }

  triggerResize() {
    // Wait for changes to be applied, then trigger textarea resize.
    this._ngZone.onStable.pipe(take(1)).subscribe(() => this.autosize.resizeToFitContent(true));
  }

  private errorHandler(error: any) {
    switch (error.status) {
      case 400:
        alert("ERRORE: L'utente non è proprietario dell'account")
        break
      case 403:
        alert("ERRORE: Operazione non autorizzata")
        break;
      case 404:
        alert("ERRORE: Evento non trovato")
        break;
      default:
        alert("ERRORE: Errore sconosciuto")
    }
  }
}
