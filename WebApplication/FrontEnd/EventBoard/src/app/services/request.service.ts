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
    const httpHeaders = new HttpHeaders({
      "Authorization": "Bearer " + JSON.parse(localStorage.getItem('token')!)
    })

    return this.http.post(this.API_SERVER_URL+"/api/create/event", {
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

  getAllEvents(): Observable<Event[]> {
    return this.http.get<Event[]>(this.API_SERVER_URL+"/api/noauth/get/events")
  }

  getEventById(id: number): Observable<Event>{
    const url = this.API_SERVER_URL+`/api/noauth/event/details/${id}`
    console.log("url: " + url)
    return this.http.get<Event>(url)
  }

  doLike(personId: number, eventId: number) {
    const httpHeaders = new HttpHeaders({
      "Authorization": "Bearer " + JSON.parse(localStorage.getItem('token')!)
    })
    return this.http.post(this.API_SERVER_URL+"/api/like", {
      person: personId,
      event: eventId
    }, {headers: httpHeaders, responseType: 'text'})
  }

  doParticipate(personId: number, eventId: number) {
    const httpHeaders = new HttpHeaders({
      "Authorization": "Bearer " + JSON.parse(localStorage.getItem('token')!)
    })
    return this.http.post(this.API_SERVER_URL+"/api/partecipation", {
      person: personId,
      event: eventId
    }, {headers: httpHeaders, responseType: 'text'})
  }
}
