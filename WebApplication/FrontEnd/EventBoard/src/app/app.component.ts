import {Component, OnInit} from '@angular/core';
import {AuthService} from "./auth/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'GoodVibes';

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.autoLogin()
  }

  private autoLogin(){
    if(!this.authService.isAuthenticated() && localStorage.getItem('token') && localStorage.getItem('username')){
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
        },
        error: error => {
          alert("ERRORE: Token scaduto, effettua nuovamente il login");
          localStorage.removeItem('token')
          localStorage.removeItem('username')
        }
      })
    }
  }
}
