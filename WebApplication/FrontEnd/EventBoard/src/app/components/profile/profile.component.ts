import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../auth/auth.service";
import {MatDialog} from "@angular/material/dialog";
import {ProfileEditDialogComponent} from "../profile-edit-dialog/profile-edit-dialog.component";
import {Router} from "@angular/router";
import {Preference} from "../../models/preference.model";
import {Location} from "../../models/location.model";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  constructor(protected authService: AuthService, private dialog: MatDialog, private router: Router) {
    // Necessary to enable reloading
    this.router.routeReuseStrategy.shouldReuseRoute = () => {return false;};
  }

  ngOnInit(): void {
    this.getData()
  }
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
        region: this.authService.user.location.region,
        city: this.authService.user.location.city,
        operationConfirmed: false
      }, disableClose: true, maxHeight: '90vh'
    })

    dialogRef.afterClosed().subscribe(result => {
        if(result.operationConfirmed && this.authService.user &&
          ((this.authService.user.username !== result.username) ||
          (this.authService.user.name !== result.name) ||
          (this.authService.user.lastName !== result.lastName) ||
          result.preferences.length > 0 /* TODO: Migliorare questo controllo, deve verificare se le preferenze sono diverse */)
        ){
          console.log(result)
          const preferences = this.buildPreferences(result.preferences)      // Must build preferences list which follow the back-end expected format
          const location: Location = {
            id: this.authService.user.location.id,
            region: result.region,
            city: result.city,
            address: null,
            latitude: 0.0,
            longitude: 0.0
          }
          this.authService.editUser(result.name, result.lastName, result.email, result.password, preferences, location).subscribe({
            next: response => {
              alert("Dati modificati con successo")
              this.router.navigateByUrl('/profile')
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
        formattedPreferences.push(preference)
      }
    }
    return formattedPreferences
  }

  private getData() {
    this.authService.getData(JSON.parse(localStorage.getItem('username'))).subscribe({
      next: (userData: any) => {
        this.authService.createUser(
          userData.id,
          userData.name,
          userData.lastName,
          userData.username,
          userData.email,
          userData.role,
          JSON.parse(localStorage.getItem('token')),
          userData.preferences,
          userData.position,
          userData.location
        )
        this.authService.isLoggedIn = true;
        console.log(this.authService.user)
      },
      error: error => { }
    })
  }
}
