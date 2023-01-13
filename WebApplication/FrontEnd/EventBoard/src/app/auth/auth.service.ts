import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../models/user.model";
import {Route, Router} from "@angular/router";
import {UpperCasePipe} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  url: string = "http://localhost:8080"
  isLoggedIn = false
  user!: User


  constructor(private http: HttpClient, private router: Router) { }

  createUser(id: number, name: string, lastName: string, username: string, email: string, role: string, token: string, preferences: string[]){
    this.user = {
      id: id,
      name: name,
      lastName: lastName,
      username: username,
      email: email,
      role: role,
      token: token,
      preferences: preferences
    }
    this.isLoggedIn = true
  }

  signUp(name: string, lastName: string, email: string, username: string, password: string, role: string){
    return this.http.post(this.url+"/api/noauth/register", {id: null, email: email, name: name, lastName: lastName, username: username, password: password, role: role, activeStatus: true, position: null})
  }

  signIn(username: string, password: string) {
    return this.http.post(this.url+"/api/noauth/authenticate", {username: username, password: password})
  }

  isAuthenticated() {
    return this.isLoggedIn
  }

  logout() {
    this.isLoggedIn = false
    this.user = null
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    this.router.navigate(['/login'])
  }

  getData(username: string) {
    const httpHeaders = new HttpHeaders({
      "Authorization": "Bearer " + JSON.parse(localStorage.getItem('token')!)
    })

    return this.http.post(this.url + `/api/user/${username}`, {}, {headers: httpHeaders})
  }

  editData( name: string, lastName: string, email: string, password: string) {
    const httpHeaders = new HttpHeaders({
      "Authorization": "Bearer " + JSON.parse(localStorage.getItem('token')!)
    })

    if(password === '')
      password = null

    return this.http.put(this.url + `/api/user/edit`, {
      id: this.user.id,
      name: name,
      lastName: lastName,
      username: this.user.username,
      password: password,
      email: email,
      activeStatus: true,
      likes: [],
      comments: [],
      reviews: [],
      preferences: [],
      position: 1,
      role: this.user.role,
      enabled: true,
      accountNonExpired: true,
      credentialsNonExpired: true,
      authorities: [],
      accountNonLocked: true,
    }, {headers: httpHeaders})
  }
}
