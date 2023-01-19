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
    //TODO: Funziona, però va gestita la scadenza del token
    if(localStorage.getItem('token')){
      if(localStorage.getItem('username')){
        this.authService.getData(JSON.parse(localStorage.getItem('username'))).subscribe({
          next: (userData: any) => {
            this.authService.createUser(
              userData.id,
              userData.name,
              userData.lastName,
              userData.username,
              userData.email,
              userData.role,
              JSON.parse(localStorage.getItem('token')),
              userData.preferences,
              userData.position,
              userData.is_not_locked)
            this.authService.isLoggedIn = true;
            console.log(this.authService.user)
          },
          error: error => { }
        })
      }
    }
  }
}
