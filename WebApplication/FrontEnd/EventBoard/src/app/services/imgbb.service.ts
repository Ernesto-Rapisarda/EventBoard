import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {IMGBB_KEY} from "../../constants";

@Injectable({
  providedIn: 'root'
})
export class ImgbbService {
  constructor(private http: HttpClient) {}

  upload(b64Image: any) {
    const formData = new FormData();
    formData.append('image', b64Image);
    console.log(formData)
    return this.http.post("/upload", formData, { params: {key: IMGBB_KEY}});    // This call is going to use the proxy detailed in /proxy.conf.json to bypass CORS
  }
}
