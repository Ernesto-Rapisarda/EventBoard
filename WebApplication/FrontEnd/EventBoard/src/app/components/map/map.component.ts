import {Component, Input, OnInit} from '@angular/core';
import {MAPBOX_KEY} from "../../../constants";
import * as mapboxgl from 'mapbox-gl';
import {MapboxService} from "../../services/mapbox.service";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  map!: mapboxgl.Map
  @Input() coordinates: Array<number> = []
  @Input() draggable: boolean

  constructor(private mapboxService: MapboxService) {
  }

  ngOnInit(): void {
    (mapboxgl as any).accessToken = MAPBOX_KEY
    this.map = new mapboxgl.Map({
      container: 'mapbox',
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [this.coordinates[0], this.coordinates[1]], // Starting [Longitude, Latitude]
      zoom: 17
    });

    this.createMarker(this.coordinates[0], this.coordinates[1], this.draggable)
  }

  createMarker(longitude: number, latitude: number, draggable: boolean) {
    const marker = new mapboxgl.Marker({
      draggable: draggable
    })
      .setLngLat([longitude, latitude])
      .addTo(this.map);

    marker.on('dragend', () => {
      console.log(marker.getLngLat())
      //this.reverseGeocode(marker.getLngLat().lng, marker.getLngLat().lat)
    })
  }


  // Unfortunately we cannot use this because we need to handle the addresses separately
  // (it won't give us house numbers for neither reverse geocoding nor forward geocoding)
  reverseGeocode(lng: number, lat: number) {
    this.mapboxService.getReverseGeocode(lng, lat).subscribe({
      next: response => {
        console.log(response)
      },
      error: error => { }
    })
  }
}
