import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ImgbbService {
  private readonly clientId = "YourAren'tGonnaFindThatHereSry";

  constructor(private http: HttpClient) {}

  upload(b64Image: any) {
    const formData = new FormData();
    formData.append('image', b64Image);
    console.log(formData)
    return this.http.post("/upload", formData, { params: {key: this.clientId}});    // This call is gonna use the proxy detailed in /proxy.conf.json
  }
}
