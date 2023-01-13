import { Component } from '@angular/core';
import {RequestService} from "../../services/request.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent {



  private min = 1;
  private max = 10;

  review: number
  comment: string

  constructor(private requestService: RequestService, private route: ActivatedRoute, private authService: AuthService, private router: Router) {
    // Necessary to enable reloading
    this.router.routeReuseStrategy.shouldReuseRoute = () => {return false;};

    this.review = 6;
    this.comment = "";
  }

  updateReview() {
    this.review++;
    if (this.review > this.max) {
      this.review = this.min;
    }
  }
  onSend() {
    const eventId = this.route.snapshot.params['id']
    const userId = this.authService.user.id
    this.requestService.addReviewToEvent(this.review, this.comment, eventId, userId).subscribe( {
      next: response => { this.router.navigateByUrl(`/event/${eventId}`) },
      error: error => {}
    })
  }

  TEST_EDITME() {
    console.log("Current comment: " + this.comment);
  }

}
