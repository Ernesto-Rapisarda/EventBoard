import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AuthService} from "../../auth/auth.service";
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";

@Component({
  selector: 'app-profile-edit-dialog',
  templateUrl: './profile-edit-dialog.component.html',
  styleUrls: ['./profile-edit-dialog.component.css']
})
export class ProfileEditDialogComponent implements OnInit, OnDestroy {
  editForm!: FormGroup
  hidePassword = true
  hidePasswordConfirm = true
  constructor(@Inject(MAT_DIALOG_DATA) protected data:  {
                                                        name: string,
                                                        lastName: string,
                                                        email: string,
                                                        password: string
                                                      }, private dialogRef: MatDialogRef<ProfileEditDialogComponent>) { }

  ngOnInit(): void {
    this.editForm = new FormGroup<any>({
        name: new FormControl(`${this.data.name}`, [Validators.required]),
        lastName: new FormControl(`${this.data.lastName}`, [Validators.required]),
        email: new FormControl(`${this.data.email}`, [Validators.required, Validators.email]),
        password: new FormControl('', [Validators.minLength(8)]),
        passwordConfirm: new FormControl(''),
     }, { validators: this.checkPasswords }
    )
  }

  ngOnDestroy(): void {
    this.data.name = this.editForm.value.name
    this.data.lastName = this.editForm.value.lastName
    this.data.email = this.editForm.value.email
    this.data.password = this.editForm.value.password
    this.dialogRef.close(this.data)
  }

  onSubmit() {
    this.dialogRef.close()
  }

  checkPasswords: ValidatorFn = (group: AbstractControl):  ValidationErrors | null => {
    let pass = group.get('password').value;
    let confirmPass = group.get('passwordConfirm').value
    return pass === confirmPass ? null : { notSame: true }
  }
}
