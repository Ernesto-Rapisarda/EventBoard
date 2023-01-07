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
        this.authService.getData(JSON.parse(localStorage.getItem('username'))).subscribe((userData: any) => {
          this.authService.createUser(userData.email, userData.username, userData.name, userData.lastName, userData.role, userData.id, JSON.parse(localStorage.getItem('token')))
          this.authService.isLoggedIn = true;
          console.log(this.authService.user)
        })
      }
    }
  }
}
