import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  constructor() {

  }

  // TODO: Lasciare un po' di avvisi per password troppo corte, verifica password errata, ecc...

  registerForm!: FormGroup

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.maxLength(16)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, /*Validators.minLength(8)*/]),
      passwordConfirm: new FormControl('', [Validators.required]),
      radioType: new FormControl('', [Validators.required])
    },{ validators: this.checkPasswords })

  }

  onSubmit() {
    console.log(this.registerForm)
  }

  /** UI elements **/
  //password
  hidePassword = true;
  hidePasswordConfirm = true;

  // Custom validator for password confirm
  checkPasswords: ValidatorFn = (group: AbstractControl):  ValidationErrors | null => {
    let pass = group.get('password').value;
    let confirmPass = group.get('passwordConfirm').value
    return pass === confirmPass ? null : { notSame: true }
  }

  /*
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
  radioAlreadyTriggered = false;
  private updateProgressBar(option: string) {
    switch (option) {

      case 'add':
        this.progressBarStatus += 20;
        break;

      case 'remove':
        this.progressBarStatus -= 20;
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

  radioChanged() {
    if(!this.radioAlreadyTriggered) {
      this.radioAlreadyTriggered = true;
      this.updateProgressBar('add');
    }
  }*/


}
