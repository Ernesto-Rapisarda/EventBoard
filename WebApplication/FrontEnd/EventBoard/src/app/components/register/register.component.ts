import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {AuthService} from "../../auth/auth.service";
import {UpperCasePipe} from "@angular/common";
import {Router} from "@angular/router";
import {interval} from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  constructor(private authService: AuthService, private router: Router) {

  }

  // TODO: Lasciare un po' di avvisi per password troppo corte, verifica password errata, ecc...

  registerForm!: FormGroup

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.maxLength(16)]),
      name: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, /*Validators.minLength(8)*/]),
      passwordConfirm: new FormControl('', [Validators.required]),
      role: new FormControl('', [Validators.required])
    },{ validators: this.checkPasswords })
  }

  onSubmit() {
    const username = this.registerForm.value.username
    const name = this.registerForm.value.name
    const lastName = this.registerForm.value.lastName
    const email = this.registerForm.value.email
    const role = this.registerForm.value.role
    const password = this.registerForm.value.password


    this.authService.signUp(name, lastName, email, username, password, role).subscribe((response: any) => {
      const token = response.token

      // TODO: Raccogliere questo e quello che è nel signIn (LoginComponent) in una funzione da qualche parte per evitare di duplicare codice
      // sets local storage variable for automatic logins
      localStorage.setItem('token', JSON.stringify(token))
      localStorage.setItem('username', JSON.stringify(username))

      if(localStorage.getItem('token')){
        this.authService.getData(username).subscribe((userData: any) => {
          this.authService.createUser(userData.email, userData.username, userData.name, userData.lastName, userData.role, userData.id, token)
          console.log(this.authService.user)
        })
        this.router.navigate(['/'])
      }
    })
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
