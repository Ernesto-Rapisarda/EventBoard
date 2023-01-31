import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AuthService} from "../../auth/auth.service";
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {RequestService} from "../../services/request.service";
import {ComuniItaService} from "../../services/comuni-ita.service";

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
  regions: string[]
  cities: string[]

  constructor(@Inject(MAT_DIALOG_DATA) protected data:  {
                                                        name: string,
                                                        lastName: string,
                                                        email: string,
                                                        region: string,
                                                        city: string
                                                        password: string,
                                                        preferences: string[],
                                                        operationConfirmed: boolean,
                                                      }, private dialogRef: MatDialogRef<ProfileEditDialogComponent>, private requestService: RequestService, private authService: AuthService, private comuniItaService: ComuniItaService) { }

  ngOnInit(): void {
    this.setEventTypes()
    this.setRegions()
    this.editForm = new FormGroup<any>({
      name: new FormControl(`${this.data.name}`, [Validators.required]),
      lastName: new FormControl(`${this.data.lastName}`, [Validators.required]),
      email: new FormControl(`${this.data.email}`, [Validators.required, Validators.email]),
      preferences: new FormControl([]),
      region: new FormControl(''),
      city: new FormControl(''),
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
    this.data.region = this.editForm.value.region
    this.data.city = this.editForm.value.city
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

  private setEventTypes() {
    this.requestService.getEventTypes().subscribe({
      next: response => {
        this.eventTypes = response.sort()
        this.typesInitialization()
      },
      error: error => { }
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

  private setRegions() {
    this.comuniItaService.getRegions().subscribe({
      next: response => {
        this.regions = (response as string[])
        if(this.data.region != ""){
          this.editForm.patchValue({
            region: this.data.region
          })
          this.setCities()
        }
      },
      error: error => { }
    })
  }

  setCities() {
    this.comuniItaService.getCities(this.editForm.value.region).subscribe({
      next: response => {
        this.cities = (response as string[])
        this.editForm.patchValue({
          city: this.data.city
        })
      },
      error: error => { }
    })
  }
}
