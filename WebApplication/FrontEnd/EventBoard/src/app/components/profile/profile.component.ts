import { Component } from '@angular/core';
import {User} from "../../models/user.model";
import {AuthService} from "../../auth/auth.service";
import {MatDialog} from "@angular/material/dialog";
import {ProfileEditDialogComponent} from "../profile-edit-dialog/profile-edit-dialog.component";
import {Router} from "@angular/router";
import {Preference} from "../../models/preference.model";
import {format} from "@cloudinary/url-gen/actions/delivery";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  constructor(protected authService: AuthService, private dialog: MatDialog, private router: Router) { }

  onProfileRemove() {
    if(this.authService.user){
      const id = this.authService.user.id
      const password = window.prompt("Per favore, reinserisci la password")
      if(id && password){
        this.authService.deleteUser(id, password).subscribe({
          next: response => {
            alert("Rimozione avvenuta con successo, ritorno alla pagina principale")
            this.router.navigateByUrl('')
            this.authService.logout()
          },
          error: error => {  }
        })
      }
      else
        alert("Operazione annullata")
    }
  }
  onProfileEdit() {
    let dialogRef = this.dialog.open(ProfileEditDialogComponent,{
      data: {
        name: this.authService.user.name,
        lastName: this.authService.user.lastName,
        email: this.authService.user.email,
        password: '',
        preferences: [],
        operationConfirmed: false
      }, disableClose: true
    })

    dialogRef.afterClosed().subscribe(result => {
        if(result.operationConfirmed && this.authService.user &&
          ((this.authService.user.username !== result.username) ||
          (this.authService.user.name !== result.name) ||
          (this.authService.user.lastName !== result.lastName) ||
          result.preferences.length > 0 /* TODO: Migliorare questo controllo, deve verificare se le preferenze sono diverse */)
        ){
          console.log(`result.preferences: ${result.preferences}`)
          const preferences = this.buildPreferences(result.preferences)      // Must build preferences list which follow the back-end expected format
          this.authService.editData(result.name, result.lastName, result.email, result.password, preferences).subscribe({
            next: response => {
              alert("Dati modificati con successo")
            },
            error: error => { /* TODO: Error handling */ }
          })
        }
      }
    )
  }

  private buildPreferences(preferences: string[]): Preference[] {
    const formattedPreferences = []
    if(this.authService.user){
      for(let item of preferences){
        const preference: Preference = {
          person: this.authService.user.id,
          event_type: item
        }
        console.log(preference)
        formattedPreferences.push(preference)
      }
    }
    return formattedPreferences
  }
}
