import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ImgurApiService {
  private readonly IMGUR_UPLOAD_URL = 'https://api.imgur.com/3/image';
  private readonly clientId = 'f877c0fff3c91cb';

  constructor(private http: HttpClient) {}

  upload(b64Image: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Client-ID ${this.clientId}`
      }),
    };
    const formData = new FormData();
    formData.append('image', b64Image);
    return this.http.post(`${this.IMGUR_UPLOAD_URL}`, formData, httpOptions);
  }
}
