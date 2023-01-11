import {Component, Input} from '@angular/core';
import {Review} from "../../models/review.model";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent {
  @Input() review: Review

  constructor(protected authService: AuthService) { }
}
