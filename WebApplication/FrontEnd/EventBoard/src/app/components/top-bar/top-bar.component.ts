import { Component } from '@angular/core';
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent {
  constructor(protected authService: AuthService) { }
}
