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

  readonly url: string = "http://localhost:8080"

  constructor(private http: HttpClient, private datePipe: DatePipe) { }

  // Create event (needs token in the header of the request)
  createEvent(date: Date, title: string, price: number, soldOut: boolean, urlPoster: string, description: string, eventType: string, position: number, organizer: number) {
    const httpHeaders = new HttpHeaders({
      "Authorization": "Bearer " + JSON.parse(localStorage.getItem('token')!)
    })

    const dateToSend = this.datePipe.transform(date, 'yyyy-MM-dd HH:mm:ss')

    return this.http.post(this.url+"/api/create/event", {
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
    return this.http.get<Event[]>(this.url+"/api/noauth/get/events")
  }


  getEventById(id: number): Observable<Event>{
    console.log('entro nella funzione')
    const url = this.url+`/api/noauth/event/details/${id}`
    console.log("url: " + url)
    return this.http.get<Event>(url)
  }
}
