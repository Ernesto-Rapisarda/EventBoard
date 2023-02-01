import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {THUMBSNAP_KEY} from "../../constants";

@Injectable({
  providedIn: 'root'
})
export class ThumbsnapService {

  constructor(private http: HttpClient) { }

  upload(b64Image: any) {
    const formData = new FormData();
    formData.append('media', b64Image);
    formData.append('key', THUMBSNAP_KEY)

    console.log(formData)
    return this.http.post("/upload/thumbsnap", formData)   // This call is going to use the proxy detailed in /proxy.conf.json to bypass CORS
  }
}
