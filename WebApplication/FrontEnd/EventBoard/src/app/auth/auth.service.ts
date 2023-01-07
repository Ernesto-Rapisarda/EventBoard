import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user.model";
import {Route, Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  url: string = "localhost:8080"
  isLoggedIn = false
  user: User


  constructor(private http: HttpClient, private router: Router) { }

  createUser(email: string, username: string, name: string, lastName: string, role: string, id: string, token: string){
    this.user = new User(email, username, name, lastName, role, id, token)
    this.isLoggedIn = true
  }

  signUp(email: string, username: string, password: string, type: string){
    return this.http.post(this.url, {email: email, username: username, password: password, type: type})
  }

  signIn(username: string, password: string){
    console.log(`username sign: ${username}`)
    console.log(`password sign: ${password}`)
    return this.http.post(this.url+"/api/noauth/authenticate", {username: username, password: password})
  }

  isAuthenticated(){
    return this.isLoggedIn
  }

  logout(){
    this.isLoggedIn = false
    this.user = null
    localStorage.removeItem('')
    this.router.navigate(['/login'])
  }
}
