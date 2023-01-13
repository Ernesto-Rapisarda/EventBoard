import {Component, OnInit} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";
import {EventComponent} from "../event/event.component";
import {Comment} from "../../models/comment.model";

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.css']
})
export class CommentFormComponent {
  text: string
  constructor(private requestService: RequestService, private route: ActivatedRoute, private authService: AuthService, private router: Router) {
    // Necessary to enable reloading
    this.router.routeReuseStrategy.shouldReuseRoute = () => {return false;};

    this.text = ""
  }
  onSend() {
    const eventId = this.route.snapshot.params['id']
    const userId = this.authService.user.id
    this.requestService.addCommentToEvent(this.text, eventId, userId).subscribe({
      next: response => { this.router.navigateByUrl(`/event/${eventId}`) },
      error: error => {}
    })
  }
}
