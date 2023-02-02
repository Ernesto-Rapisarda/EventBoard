import {Component, Input, NgZone, ViewChild} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {take} from 'rxjs/operators';

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent {
  private readonly MIN = 1;
  private readonly MAX = 5;

  rating: number = 4
  text: string = ''
  @Input() eventDate: Date
  @ViewChild('autosize') autosize: CdkTextareaAutosize;


  constructor(private _ngZone: NgZone, private requestService: RequestService, private route: ActivatedRoute, private authService: AuthService, private router: Router) {
    // Necessary to enable reloading
    this.router.routeReuseStrategy.shouldReuseRoute = () => {return false;};
  }

  updateRating() {
    if (++this.rating > this.MAX)
      this.rating = this.MIN;
  }
  onSend() {
    if(this.text !== ''){
      const eventId = this.route.snapshot.params['id']
      const userId = this.authService.user.id
      this.requestService.addReview(this.rating, this.text, eventId, userId).subscribe( {
        next: response => { this.router.navigateByUrl(`/event/${eventId}`) },
        error: error => {alert("ERRORE: Non è stato possibile memorizzare la recensione")}
      })
    }
    else
      alert("ERRORE: Testo della recensione vuoto!")
  }

  isBeforeEventDate() {
    const now = new Date()
    const eventDateFormatted = new Date(this.eventDate)
    return now < eventDateFormatted
  }

  /** DO NOT ABSOLUTELY REMOVE, IT SEEMS UNUSED BUT IT IS USED BY TEXTAREAs FOR RESIZING*/
  triggerResize() {
    // Wait for changes to be applied, then trigger textarea resize.
    this._ngZone.onStable.pipe(take(1)).subscribe(() => this.autosize.resizeToFitContent(true));
  }
}
