import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup
  constructor(private authService: AuthService, private router: Router) { }



  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }


  /** User information **/



  /** UI elements **/
  /* password */
  hidePassword = true;

  onSubmit() {
    const username = this.loginForm.value.username
    const password = this.loginForm.value.password

    this.authService.signIn(username, password).subscribe({
      next: (response: any) =>{
        const token = response.token

        // sets local storage variable for automatic logins
        localStorage.setItem('token', JSON.stringify(token))
        localStorage.setItem('username', JSON.stringify(username))

        if(localStorage.getItem('token')){
          this.authService.getData(username).subscribe({
            next: (userData: any) => {
              this.authService.createUser(userData.id, userData.name, userData.lastName, userData.username, userData.email, userData.role, token, userData.preferences, userData.position)
              console.log(this.authService.user)
            },
            error: error => { this.errorHandler(error) },
          })

          this.router.navigate([''])
        }
      }
    })
  }

  private errorHandler(error: number){
    switch (error) {
      case 403:
        alert("ERRORE: Le credenziali inserite non sono valide! Controlla e riprova")
        break
      default:
        alert("ERRORE: Errore sconosciuto")
        break
    }
  }
}
