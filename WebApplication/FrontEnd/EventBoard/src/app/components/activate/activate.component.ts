import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.css']
})
export class ActivateComponent implements OnInit {
  constructor(private authService: AuthService, private route: ActivatedRoute, private router: Router, private snackbarService: SnackbarService) {
  }

  ngOnInit(): void {
    const token = this.route.snapshot.params['token']
    if(token){
      this.authService.activate(token).subscribe({
        next: response => {
          this.snackbarService.openSnackBar("Attivazione avvenuta con successo.", "OK")
          this.router.navigateByUrl('')
        },
        error: error => { this.snackbarService.openSnackBar("Errore durante l'attivazione.", "OK"); this.router.navigateByUrl('') }
      })
    }
    else {
      this.snackbarService.openSnackBar("ERRORE: Parametro token non trovato", "OK")
    }
  }
}
