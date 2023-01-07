import { Component } from '@angular/core';
import {User} from "../../models/user.model";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  constructor(protected authService: AuthService) {
  }
}
