import {Component, Input} from '@angular/core';
import {Comment} from "../../models/comment.model";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent {
  @Input() comment: Comment

  constructor(protected authService: AuthService) { }
}
