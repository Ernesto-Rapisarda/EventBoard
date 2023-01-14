import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const path = route.url[0].path
    console.log("SONO ENTRATO")
    if(localStorage.getItem('token'))
    console.log(this.authService.isAuthenticated())
    // if the user is trying to go to:
    if( (path === 'profile' && !this.authService.isAuthenticated()) ||                                                           // profile page, but they are not authenticated
        (path === 'create-event' && (!this.authService.isAuthenticated() || this.authService.user.role !== "ORGANIZER")) ||      // create event page, but either they are not authenticated or they are not an organizer
        (((path === 'register') || (path === 'login')) && this.authService.isAuthenticated())                                    // login/register page, but they are authenticated
    ){
      // block this operation
      this.router.navigate(['/'])
      alert("Accesso negato")
      return false
    }

    // consent every other
    return true;
  }

}
