import { Component } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor() {
    /* quando i campi username e password vengono modificati, viene invocata una funzione che aggiorna la progress bar */
    this.username.valueChanges.subscribe(() => {
      this.usernameChanged();
    });
    this.password.valueChanges.subscribe(() => {
      this.passwordChanged();
    });
  }


  /** User information **/
  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);


  /** UI elements **/
  /* password */
  hidePassword = true;

  /* progress bar */
  progressBarStatus = 0;
  usernameAlreadyTriggered = false;
  passwordAlreadyTriggered = false;
  private updateProgressBar(option: string) {
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
}
