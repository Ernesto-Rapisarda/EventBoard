import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup
  authService: AuthService
  constructor() {
    /* quando i campi username e password vengono modificati, viene invocata una funzione che aggiorna la progress bar */
    /*this.loginForm.value.username.valueChanges.subscribe(() => {
      this.usernameChanged();
    });
    this.password.valueChanges.subscribe(() => {
      this.passwordChanged();
    });*/
  }



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

  /* progress bar */
  progressBarStatus = 0;
  usernameAlreadyTriggered = false;
  passwordAlreadyTriggered = false;
  /*private updateProgressBar(option: string) {
    switch (option) {

      case 'add':
        this.progressBarStatus += 50;
        break;

      case 'remove':
        this.progressBarStatus -= 50;
        break;

      default:
        break;

    }
  }
  usernameChanged() {
    if(!this.usernameAlreadyTriggered) {
      this.usernameAlreadyTriggered = true;
      this.updateProgressBar('add');
    }
    else {
      if(this.username.value === '') {
        this.usernameAlreadyTriggered = false;
        this.updateProgressBar('remove');
      }
    }
  }
  passwordChanged() {
    if(!this.passwordAlreadyTriggered) {
      this.passwordAlreadyTriggered = true;
      this.updateProgressBar('add');
    }
    else {
      if(this.password.value === '') {
        this.passwordAlreadyTriggered = false;
        this.updateProgressBar('remove');
      }
    }
  }

  getButtonStatus(): string {
    if (this.progressBarStatus === 100) {
      return 'enabled';
    }
    return 'disabled';
  }
*/

  onSubmit() {
    const username = this.loginForm.value.username
    const password = this.loginForm.value.password

    console.log("username: " + username)
    console.log("password: " + password)


    this.authService.signIn(username, password).subscribe((data: any) => {
      /*console.log("username: " + data.username)
      console.log("name: " + data.name)
      console.log("lastName: " + data.lastName)
      console.log("role: " + data.role)
      console.log("id: " + data.id)
      console.log("token: " + data.token)*/
      this.authService.createUser(data.email, data.username, data.name, data.lastName, data.role, data.id, data.token)

      // sets local storage variable for automatic logins
      localStorage.setItem('user', JSON.stringify(this.authService.user))
      //console.log("loggato")
    })


  }
}
