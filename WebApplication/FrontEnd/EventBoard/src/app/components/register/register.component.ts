import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {RequestService} from "../../services/request.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  constructor(private authService: AuthService, private router: Router, private requestService: RequestService) { }

  // TODO: Lasciare un po' di avvisi per password troppo corte, verifica password errata, ecc...

  registerForm!: FormGroup
  ngOnInit(): void {
    this.registerForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.maxLength(16)]),
      name: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      passwordConfirm: new FormControl('', [Validators.required]),
      role: new FormControl('', [Validators.required])
    },{ validators: this.checkPasswords })
  }

  // Called when the form is submitted
  onSubmit() {
    const username = this.registerForm.value.username
    const name = this.registerForm.value.name
    const lastName = this.registerForm.value.lastName
    const email = this.registerForm.value.email
    const role = this.registerForm.value.role
    const password = this.registerForm.value.password


    this.authService.signUp(name, lastName, email, username, password, role).subscribe({
      next: (response: any) =>{
        const token = response.token

        // sets local storage variable for automatic logins
        localStorage.setItem('token', JSON.stringify(token))
        localStorage.setItem('username', JSON.stringify(username))

        if(localStorage.getItem('token')){
          this.authService.getData(username).subscribe((userData: any) => {
            this.authService.createUser(userData.id, userData.name, userData.lastName, userData.username, userData.email, userData.role, token, [])
            console.log(this.authService.user)
          })
          this.router.navigate([''])
        }
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
}
