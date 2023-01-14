import { Component } from '@angular/core';
import {User} from "../../models/user.model";
import {AuthService} from "../../auth/auth.service";
import {MatDialog} from "@angular/material/dialog";
import {ProfileEditDialogComponent} from "../profile-edit-dialog/profile-edit-dialog.component";
import {Router} from "@angular/router";

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
        password: ''
      }, disableClose: true
    })

    dialogRef.afterClosed().subscribe(result => {
        if(
          this.authService.user.name === result.name &&
          this.authService.user.lastName === result.lastName &&
          this.authService.user.email === result.email &&
          (!result.password)
        ){ alert("Nessun dato cambiato, richiesta annullata") }
        else {
            alert("Richiesta la modifica dei dati")
            this.authService.editData(result.name, result.lastName, result.email, result.password).subscribe((result: any) => {
              console.log(result)
            })
        }
      }
    )
  }
}
