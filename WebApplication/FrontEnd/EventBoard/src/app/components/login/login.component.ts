import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup
  /*authService: AuthService*/
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
          this.authService.getData(username).subscribe((userData: any) => {
            this.authService.createUser(userData.id, userData.name, userData.lastName, userData.username, userData.email, userData.role, token)
            console.log(this.authService.user)
          })
          this.router.navigate([''])
        }
      }
    })
  }
}
