import {Component, NgZone, ViewChild} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {take} from "rxjs/operators";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.css']
})
export class CommentFormComponent {
  text: string = ''
  @ViewChild('autosize') autosize: CdkTextareaAutosize;
  constructor(private requestService: RequestService, private route: ActivatedRoute, private authService: AuthService, private router: Router, private snackbarService: SnackbarService, private _ngZone: NgZone) {
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
      this.snackbarService.openSnackBar("ERRORE: Testo del commento vuoto!", "OK")
  }

  /** DO NOT ABSOLUTELY REMOVE, IT SEEMS UNUSED BUT IT IS USED BY TEXTAREAs FOR RESIZING*/
  triggerResize() {
    // Wait for changes to be applied, then trigger textarea resize.
    this._ngZone.onStable.pipe(take(1)).subscribe(() => this.autosize.resizeToFitContent(true));
  }

  private errorHandler(error: any) {
    switch (error.status) {
      case 400:
        this.snackbarService.openSnackBar("ERRORE: L'utente non è proprietario dell'account", "OK")
        break
      case 403:
        this.snackbarService.openSnackBar("ERRORE: Operazione non autorizzata", "OK")
        break;
      case 404:
        this.snackbarService.openSnackBar("ERRORE: Evento non trovato", "OK")
        break;
      default:
        this.snackbarService.openSnackBar("ERRORE: Errore sconosciuto", "OK")
    }
  }
}
