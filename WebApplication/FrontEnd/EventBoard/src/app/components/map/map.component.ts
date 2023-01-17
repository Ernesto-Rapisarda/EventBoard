import {Component, OnInit} from '@angular/core';
import {MAPBOX_KEY} from "../../../constants";
// @ts-ignore
import * as mapboxgl from 'mapbox-gl';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  ngOnInit(): void {
    mapboxgl.accessToken = MAPBOX_KEY
    const map = mapboxgl.Map({
      container: 'mapbox',
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [16.2260993, 39.363239], // Starting [Longitude, Altitude]
      zoom: 17
    });
  }
}
