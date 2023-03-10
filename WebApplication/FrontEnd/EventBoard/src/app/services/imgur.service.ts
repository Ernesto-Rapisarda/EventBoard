import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {IMGUR_CLIENT_ID} from "../../constants";
import {timeout} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ImgurService {

  constructor(private http: HttpClient) { }

  upload(b64Image: any) {
    const formData = new FormData()

    formData.append('image', b64Image)

    const headers = this.getAuthorizationHeader()

    return this.http.post("/upload/imgur", {image: b64Image}, {
      headers: headers
    })   // This call is going to use the proxy detailed in /proxy.conf.json to bypass CORS
      .pipe(
        timeout(10000)      // After 10 seconds return timeout
      )
  }
  private getAuthorizationHeader(){
    return new HttpHeaders({
      "Authorization": "Client-ID " + IMGUR_CLIENT_ID
    })
  }
}
