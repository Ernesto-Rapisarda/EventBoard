import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Event} from "../models/event.model";
import {DatePipe} from "@angular/common";
import {User} from "../models/user.model";


@Injectable({
  providedIn: 'root'
})
export class RequestService {
  readonly API_SERVER_URL: string = "http://localhost:8080"

  constructor(private http: HttpClient, private datePipe: DatePipe) {}

  // Create event (needs token in the header of the request)
  createEvent(date: Date, title: string, price: number, soldOut: boolean, urlPoster: string, description: string, eventType: string, position: number, organizer: number) {
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
      description: description,
      eventType: eventType,
      position: position,
      organizer: organizer
    }, {headers: httpHeaders})
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

  getOrganizer(id: number) {
    const url = this.API_SERVER_URL + `/api/noauth/organizer/${id}`
    return this.http.get(url)
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
  getAuthorizationHeader() {
    return new HttpHeaders({
      "Authorization": "Bearer " + JSON.parse(localStorage.getItem('token')!)
    })
  }
}
