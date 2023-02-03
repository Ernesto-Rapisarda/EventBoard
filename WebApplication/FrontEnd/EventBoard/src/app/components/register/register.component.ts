import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {RequestService} from "../../services/request.service";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  constructor(private authService: AuthService, private router: Router, private requestService: RequestService, private snackbarService: SnackbarService) { }

  registerForm!: FormGroup
  ngOnInit(): void {
    this.registerForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16)]),
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

    if(confirm("Vuoi creare un account con i seguenti dati?\n" + this.getConfirmString())) {
      this.authService.signUp(name, lastName, email, username, password, role).subscribe({
        next: response => {
          this.snackbarService.openSnackBar("Ti sei registrato con successo!\nPer poter utilizzare il tuo profilo devi prima eseguire l'attivazione tramite il link che ti è stato recapitato sull'email", "OK")
          this.router.navigate([''])
        },
        error: error => {
          this.snackbarService.openSnackBar("Errore: C'è stato un errore in fase di registrazione, ti consigliamo di riprovare", "OK") }
      })
    }
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

  // SERVICE FUNCTIONS
  private getConfirmString() {
    const str = "Username: " + this.registerForm.value.username + "\n" +
      "Email: " + this.registerForm.value.email + "\n" +
      "Nome: " + this.registerForm.value.name + "\n" +
      "Cognome: " + this.registerForm.value.lastName + "\n" +
      "Password: " + '*'.repeat(this.registerForm.value.password.length)
    return str
  }
}
