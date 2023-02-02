import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MAPBOX_API_URL, MAPBOX_KEY} from "../../constants";

@Injectable({
  providedIn: 'root'
})
export class MapboxService {
  constructor(private http: HttpClient) {}

  getForwardGeocode(region: string, city: string, address: string) {
    const query = city + ", " + region + ", Italy"
    return this.http.get(MAPBOX_API_URL+"/mapbox.places"+`/${query}.json?access_token=${MAPBOX_KEY}`)
  }

  getReverseGeocode(longitude: number, latitude: number) {
    return this.http.get(MAPBOX_API_URL+"/mapbox.places"+`/${longitude},${latitude}.json?access_token=${MAPBOX_KEY}`)
  }
}
