import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../models/user.model";
import {Router} from "@angular/router";
import {Preference} from "../models/preference.model";
import {API_SERVER_URL} from "../../constants";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLoggedIn = false
  user!: User

  constructor(private http: HttpClient, private router: Router) { }

  createUser(id: number, name: string, lastName: string, username: string, email: string, role: string, token: string, preferences: Preference[]){
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

  editUser(name: string, lastName: string, email: string, password: string, preferences: Preference[]) {
    const httpHeaders = this.getAuthorizationHeader()
    const url = API_SERVER_URL + '/api/user/update'

    if(password === '')
      password = null

    return this.http.put(url, {
      id: this.user.id,
      name: name,
      lastName: lastName,
      username: this.user.username,
      email: email,
      password: password,
      position: 1,
      role: this.user.role,
      is_not_locked: true,
      preferences: preferences
    }, {headers: httpHeaders, responseType: "text"})
  }

  deleteUser(id: number, password: string) {
    const httpHeaders = this.getAuthorizationHeader()
    const url = API_SERVER_URL + '/api/user/delete'
    return this.http.delete(url, {headers: httpHeaders, body: {password: password, id: id}, responseType: 'text'})
  }

  signUp(name: string, lastName: string, email: string, username: string, password: string, role: string){
    const url = API_SERVER_URL + "/api/noauth/register"
    return this.http.post(url, {
      id: null,
      email: email,
      name: name,
      lastName: lastName,
      username: username,
      password: password,
      role: role,
      activeStatus: true,
      position: null
    })
  }

  activate(token: string) {
    const url = API_SERVER_URL + `/api/noauth/activate/${token}`
    return this.http.get(url, {responseType: "text"})
  }

  signIn(username: string, password: string) {
    const url = API_SERVER_URL + "/api/noauth/authenticate"
    return this.http.post(url, {
      username: username,
      password: password
    })
  }

  isAuthenticated() {
    return this.isLoggedIn
  }

  logout() {
    this.isLoggedIn = false
    this.user = null
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    this.router.navigateByUrl('/login')
  }

  getData(username: string) {
    const httpHeaders = this.getAuthorizationHeader()
    const url = API_SERVER_URL + `/api/user/${username}`

    return this.http.post(url, {}, {headers: httpHeaders})
  }

  private getAuthorizationHeader() {
    return new HttpHeaders({
      "Authorization": "Bearer " + JSON.parse(localStorage.getItem('token')!)
    })
  }

  isAdmin() {
    return this.user.role === "ADMIN"
  }
}
