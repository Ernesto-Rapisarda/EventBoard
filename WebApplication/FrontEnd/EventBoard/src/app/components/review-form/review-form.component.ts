import {Component} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";

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

  constructor(private requestService: RequestService, private route: ActivatedRoute, private authService: AuthService, private router: Router) {
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
        error: error => {}
      })
    }
    else
      alert("ERRORE: Testo della recensione vuoto!")
  }
}
