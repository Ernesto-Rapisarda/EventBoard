import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../auth/auth.service";
import {MatDialog} from "@angular/material/dialog";
import {ProfileEditDialogComponent} from "../profile-edit-dialog/profile-edit-dialog.component";
import {Router} from "@angular/router";
import {Preference} from "../../models/preference.model";
import {Location} from "../../models/location.model";
import {Event} from "../../models/event.model";
import {RequestService} from "../../services/request.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  organizerEvents: Array<Event>
  constructor(protected authService: AuthService, private dialog: MatDialog, private router: Router, private requestService: RequestService) {
    // Necessary to enable reloading
    this.router.routeReuseStrategy.shouldReuseRoute = () => {return false;};
  }

  ngOnInit(): void {
    this.getData()
    if(this.authService.isOrganizer()){
      this.getOrganizerEvents()
    }
  }
  onProfileRemove() {
    if(this.authService.user){
      const id = this.authService.user.id
      const password = window.prompt("Per favore, reinserisci la password")
      if(id && password){
        this.authService.deleteUser(id, password).subscribe({
          next: response => {
            alert("Rimozione avvenuta con successo")
            this.router.navigateByUrl('')
            this.authService.logout()
          },
          error: error => {
            this.errorHandler(error)
          }
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
        console.log(result)
        console.log(this.authService.user)
        if(result.operationConfirmed && this.authService.user &&
          (
            (this.authService.user.name !== result.name) ||
            (this.authService.user.lastName !== result.lastName) ||
            (this.differentPreferences(result.preferences)) ||
            (this.differentPosition(result.region, result.city)) ||
            (result.password !== '')
          )
        ){
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
            error: error => { this.errorHandler(error) }
          })
        }
        else
          alert("Operazione annullata, nessun dato modificato")
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
      error: error => { this.errorHandler(error) }
    })
  }

  private getOrganizerEvents() {
    this.organizerEvents = new Array<Event>()

    this.requestService.getOrganizer(this.authService.user.id).subscribe({
      next: (response: any) => {
        for (let element in response.events) {
          let event = {
            id: response.events[element].id,
            title: response.events[element].title,
            date: response.events[element].date,
            urlPoster: response.events[element].urlPoster,
            position: response.events[element].position,
            organizerFullName: response.events[element].organizer.toString(),
          }
          this.organizerEvents.push(event)
        }
      },
      error: error => { this.errorHandler(error) }
    })
  }

  private differentPreferences(preferences: string[]): boolean {
    let equalPreferences = 0
    if(this.authService.user){
      // If the number of preferences is different, then the preferences are different
      if(preferences.length !== this.authService.user.preferences.length)
        return true

      // Count the number of equal preferences
      for(let preference of this.authService.user.preferences)
        if(preferences.includes(preference.event_type))
          ++equalPreferences

      // If the number of equal preferences is equal to the number of preferences, then the preferences are the same
      if(equalPreferences === this.authService.user.preferences.length)
        return false
      return true
    }
    return false
  }

  private differentPosition(region: string, city: string): boolean {
    return (region !== this.authService.user.location.region) || (city !== this.authService.user.location.city)
  }

  private errorHandler(error: any) {
    switch (error.status) {
      case 400:
        alert("ERRORE: Operazione non valida")
        break;
      case 403:
        alert("ERRORE: Non hai i permessi per eseguire questa operazione")
        break;
      case 404:
        alert("ERRORE: Errore di elaborazione")
    }
  }
}
