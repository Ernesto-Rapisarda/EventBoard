import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Event} from "../models/event.model";


@Injectable({
  providedIn: 'root'
})
export class RequestService {

  readonly url: string = "http://localhost:8080"
  constructor(private http: HttpClient) { }

  getAllEvents(): Observable<Event[]> {
    return this.http.get<Event[]>(this.url+"/api/noauth/get/events")
  }
}
