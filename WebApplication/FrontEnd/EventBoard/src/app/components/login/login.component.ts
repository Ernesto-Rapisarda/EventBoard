import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup
  /*authService: AuthService*/
  constructor(private authService: AuthService) { }



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

    console.log("username componente: " + username)
    console.log("password componente: " + password)

    this.authService.signIn(username, password).subscribe((response: any) => {
      const token = response.token

      // sets local storage variable for automatic logins
      localStorage.setItem('token', JSON.stringify(token))

        if(token){
          this.authService.getData(token, username).subscribe((userData: any) => {
            this.authService.createUser(userData.email, userData.username, userData.name, userData.lastName, userData.role, userData.id, token)
            console.log(this.authService.user)
          })
        }
    })
  }
}
