import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {COMUNI_ITA_API_URL} from "../../constants";

@Injectable({
  providedIn: 'root'
})
export class ComuniItaService {

  constructor(private http: HttpClient) { }

  getRegions() { return this.http.get(COMUNI_ITA_API_URL+'/regioni') }
  getCities(region: string) { return this.http.get(COMUNI_ITA_API_URL+`/comuni/${region}?onlyname=true`) }
}
