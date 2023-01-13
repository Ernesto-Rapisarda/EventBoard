import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ImgApiService {
  private readonly IMGUR_UPLOAD_URL = 'https://api.imgbb.com/1/upload';
  private readonly clientId = '75ce64b598366d18ef4089ce2adfd652';

  constructor(private http: HttpClient) {}

  upload(b64Image: any) {
    const formData = new FormData();
    formData.append('image', b64Image);
    console.log(formData)
    return this.http.post(`http://localhost:6969/${this.IMGUR_UPLOAD_URL}?key=${this.clientId}`, formData);
  }
}
