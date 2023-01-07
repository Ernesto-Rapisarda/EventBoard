import {Component, OnInit} from '@angular/core';
import {AuthService} from "./auth/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'EventBoard';

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    if(localStorage.getItem('user')){
      const user = JSON.parse(localStorage.getItem('user'))
      this.authService.createUser(user.email, user.username, user.name, user.lastName, user.role, user.id, user._token)
    }
  }
}
