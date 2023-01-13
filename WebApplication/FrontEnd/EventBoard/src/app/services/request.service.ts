import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, windowWhen} from "rxjs";
import {Event} from "../models/event.model";
import {DatePipe} from "@angular/common";
import {User} from "../models/user.model";
import {AuthService} from "../auth/auth.service";


@Injectable({
  providedIn: 'root'
})
export class RequestService {
  readonly API_SERVER_URL: string = "http://localhost:8080"

  constructor(private http: HttpClient, private datePipe: DatePipe, private authService: AuthService) {}

  // EVENT RELATED REQUESTS

  // Create event (needs token in the header of the request)
  createEvent(date: Date, title: string, price: number, soldOut: boolean, urlPoster: string, urlTicket: string, description: string, eventType: string, position: number, organizer: number) {
    const dateToSend = this.datePipe.transform(date, 'yyyy-MM-dd HH:mm:ss')
    const httpHeaders = this.getAuthorizationHeader()
    const url = this.API_SERVER_URL+"/api/create/event"
    return this.http.post(url, {
      id: null,
      date: dateToSend,
      title: title,
      price: price,
      soldOut: soldOut,
      urlPoster: urlPoster,
      urlTicket: urlTicket,
      description: description,
      eventType: eventType,
      position: position,
      organizer: organizer
    }, {headers: httpHeaders})
  }

  // Remove event
  deleteEvent(id: number) {
    let message = ''
    if(this.isAdmin())
      message = window.prompt("Qual è il motivo della rimozione?")

    const httpHeaders = this.getAuthorizationHeader()
    const url = this.API_SERVER_URL+`/api/delete/event/${id}`
    return this.http.delete(url, {headers: httpHeaders, body: {message:message}, responseType: 'text'})
  }


  getEventTypes(): Observable<string[]> {
    const url = this.API_SERVER_URL+'/api/noauth/type/events'
    return this.http.get<string[]>(url)
  }

  getAllEvents(): Observable<Event[]> {
    const url = this.API_SERVER_URL+"/api/noauth/get/events"
    return this.http.get<Event[]>(url)
  }

  getEventById(id: number): Observable<Event>{
    const url = this.API_SERVER_URL+`/api/noauth/event/details/${id}`
    return this.http.get<Event>(url)
  }

  doLike(personId: number, eventId: number) {
    const httpHeaders = this.getAuthorizationHeader()
    const url = this.API_SERVER_URL+"/api/like"

    return this.http.post(url, {
      person: personId,
      event: eventId
    }, {headers: httpHeaders, responseType: 'text'})
  }

  doParticipate(personId: number, eventId: number) {
    const httpHeaders = this.getAuthorizationHeader()
    const url = this.API_SERVER_URL+"/api/partecipation"

    return this.http.post(url, {
      person: personId,
      event: eventId
    }, {headers: httpHeaders, responseType: 'text'})
  }

  // ORGANIZER PROFILE RELATED REQUESTS
  getOrganizer(id: number) {
    const url = this.API_SERVER_URL + `/api/noauth/organizer/${id}`
    return this.http.get(url)
  }

  // COMMENT RELATED REQUESTS
  addCommentToEvent(text: string, eventId: number, userId: number){
    const url = this.API_SERVER_URL + "/api/comment/add"
    const httpHeaders = this.getAuthorizationHeader()
    return this.http.post(url, {
      id: null,
      date: null,
      message: text,
      person: userId,
      event: eventId
    }, {headers: httpHeaders, responseType: 'text'})
  }

  addReviewToEvent(review: number, text: string, eventId: number, userId: number){
    const url = this.API_SERVER_URL + "/api/review/add"
    const httpHeaders = this.getAuthorizationHeader()
    return this.http.post(url, {
      person: userId,
      event: eventId,
      date:null,
      message: text,
      rating: review
    }, {headers: httpHeaders, responseType: 'text'})
  }

  deleteComment(id: number) {
    let message = ''
    if(this.isAdmin())
      message = window.prompt("Qual è il motivo della rimozione?")

    const url = this.API_SERVER_URL + `/api/comment/delete/${id}`
    const httpHeaders = this.getAuthorizationHeader()
    return this.http.delete(url, {headers: httpHeaders, body: {message:message}, responseType: 'text'})
  }

  //  GET AUTHORIZATION HEADER (SERVICE FUNCTION)
  private getAuthorizationHeader() {
    return new HttpHeaders({
      "Authorization": "Bearer " + JSON.parse(localStorage.getItem('token')!)
    })
  }

  private isAdmin() {
    return this.authService.user.role == "ADMIN"
  }
}
