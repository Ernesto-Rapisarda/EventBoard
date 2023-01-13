import { Component } from '@angular/core';

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent {

  private min = 1;
  private max = 10;

  review: number = 6;

  private updateReview() {
    this.review++;
    if (this.review > this.max) {
      this.review = this.min;
    }
  }


}
