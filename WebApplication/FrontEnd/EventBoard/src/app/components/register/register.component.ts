import { Component } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor() {
    this.username.valueChanges.subscribe(() => {
      this.usernameChanged();
    });
    this.email.valueChanges.subscribe(() => {
      this.emailChanged();
    });
    this.password.valueChanges.subscribe(() => {
      this.passwordChanged();
    });
    this.passwordConfirm.valueChanges.subscribe(() => {
      this.passwordConfirmChanged();
    });
  }

  username = new FormControl('', [Validators.required]);
  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);
  passwordConfirm = new FormControl('', [Validators.required]);


  /** UI elements **/
  /* password */
  hidePassword = true;
  hidePasswordConfirm = true;

  getErrorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }

    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
  getButtonStatus(): string {
    if (this.progressBarStatus === 100) {
      return 'enabled';
    }
    return 'disabled';
  }


  progressBarStatus = 0;
  usernameAlreadyTriggered = false;
  emailAlreadyTriggered = false;
  passwordAlreadyTriggered = false;
  passwordConfirmAlreadyTriggered = false;
  private updateProgressBar(option: string) {
    switch (option) {

      case 'add':
        this.progressBarStatus += 25;
        break;

      case 'remove':
        this.progressBarStatus -= 25;
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
  emailChanged() {
    if(!this.emailAlreadyTriggered) {
      this.emailAlreadyTriggered = true;
      this.updateProgressBar('add');
    }
    else {
      if(this.email.value === '') {
        this.emailAlreadyTriggered = false;
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
  passwordConfirmChanged() {
    if(!this.passwordConfirmAlreadyTriggered) {
      this.passwordConfirmAlreadyTriggered = true;
      this.updateProgressBar('add');
    }
    else {
      if(this.passwordConfirm.value === '') {
        this.passwordConfirmAlreadyTriggered = false;
        this.updateProgressBar('remove');
      }
    }
  }
}
