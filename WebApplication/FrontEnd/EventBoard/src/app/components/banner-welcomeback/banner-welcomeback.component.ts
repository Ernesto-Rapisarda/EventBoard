import {Component} from '@angular/core';
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-banner-welcomeback',
  templateUrl: './banner-welcomeback.component.html',
  styleUrls: ['./banner-welcomeback.component.css']
})
export class BannerWelcomebackComponent {
  constructor(protected authService: AuthService) { }

}
