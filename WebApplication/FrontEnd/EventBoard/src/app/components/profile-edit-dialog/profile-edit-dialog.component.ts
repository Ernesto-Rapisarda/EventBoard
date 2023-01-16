import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AuthService} from "../../auth/auth.service";
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {RequestService} from "../../services/request.service";

@Component({
  selector: 'app-profile-edit-dialog',
  templateUrl: './profile-edit-dialog.component.html',
  styleUrls: ['./profile-edit-dialog.component.css']
})
export class ProfileEditDialogComponent implements OnInit {
  editForm!: FormGroup
  hidePassword = true
  hidePasswordConfirm = true
  operationConfirmed = false
  eventTypes: string[];

  constructor(@Inject(MAT_DIALOG_DATA) protected data:  {
                                                        name: string,
                                                        lastName: string,
                                                        email: string,
                                                        password: string
                                                        preferences: string[]
                                                        operationConfirmed: boolean
                                                      }, private dialogRef: MatDialogRef<ProfileEditDialogComponent>, private requestService: RequestService, private authService: AuthService) { }

  // TODO: Deve riempire preferenze con chip di preferenze già espresse
  ngOnInit(): void {
    this.requestService.getEventTypes().subscribe({
      next: response => {
        this.eventTypes = response.sort()
        this.typesInitialization()
    }})

    this.editForm = new FormGroup<any>({
      name: new FormControl(`${this.data.name}`, [Validators.required]),
      lastName: new FormControl(`${this.data.lastName}`, [Validators.required]),
      email: new FormControl(`${this.data.email}`, [Validators.required, Validators.email]),
      preferences: new FormControl([]),
      password: new FormControl('', [Validators.minLength(8)]),
      passwordConfirm: new FormControl('')
     }, { validators: this.checkPasswords }
    )
  }

  onSubmit() {
    this.data.name = this.editForm.value.name
    this.data.lastName = this.editForm.value.lastName
    this.data.email = this.editForm.value.email
    this.data.password = this.editForm.value.password
    this.data.operationConfirmed = this.operationConfirmed
    this.data.preferences = this.editForm.value.preferences
    this.dialogRef.close(this.data)
  }

  onConfirm() {
    this.operationConfirmed = true
  }

  onCancel() {
    this.operationConfirmed = false
  }
  checkPasswords: ValidatorFn = (group: AbstractControl):  ValidationErrors | null => {
    let pass = group.get('password').value;
    let confirmPass = group.get('passwordConfirm').value
    return pass === confirmPass ? null : { notSame: true }
  }

  // Called when a chip is removed
  onTypeRemoved(type: string) {
    const formPreferences = this.editForm.value.preferences as string[]
    this.remove(formPreferences, type)
    this.editForm.patchValue({
      preferences: formPreferences
    })
  }

  typesInitialization() {
    if(this.authService.user){
      let formPreferences: string[] = []

      for(let preference of this.authService.user.preferences)
        formPreferences.push(preference.event_type)

      this.editForm.patchValue({
        preferences: formPreferences
      })
    }
  }

  private remove(array: string[], toRemove: string) {
    const index = array.indexOf(toRemove)
    if (index !== -1)
      array.splice(index, 1)
  }
}
