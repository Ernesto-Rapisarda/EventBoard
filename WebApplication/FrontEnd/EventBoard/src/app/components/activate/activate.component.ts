import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.css']
})
export class ActivateComponent implements OnInit {
  constructor(private authService: AuthService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    const token = this.route.snapshot.params['token']
    if(token){
      this.authService.activate(token).subscribe({
        next: response => {
          alert("Attivazione avvenuta con successo, ritorno alla pagina principale")
          this.router.navigateByUrl('')
        },
        error: error => { }
      })
    }
    else {
      alert("ERRORE: Parametro token non trovato")
    }
  }
}
