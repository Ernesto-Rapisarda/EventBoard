import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MapboxService} from "../../services/mapbox.service";

@Component({
  selector: 'app-location-chooser-dialog',
  templateUrl: './location-chooser-dialog.component.html',
  styleUrls: ['./location-chooser-dialog.component.css']
})
export class LocationChooserDialogComponent {

  latitude: number
  longitude: number
  address: string

  constructor(@Inject(MAT_DIALOG_DATA) protected data:  {
    longitude: number,
    latitude: number,
    address: string,
    operationConfirmed: boolean
  }, private dialogRef: MatDialogRef<LocationChooserDialogComponent>, private mapboxService: MapboxService) {
    this.latitude = data.latitude
    this.longitude = data.longitude
  }

  onConfirm() {
    this.data.latitude = this.latitude
    this.data.longitude = this.longitude
    this.data.address = this.address
    this.data.operationConfirmed = true
    this.dialogRef.close(this.data)
  }

  onCancel() {
    this.dialogRef.close(this.data)
  }

  onCoordinatesReceived(coordinates: number[]) {
    // coordinates = [lng, lat]
    this.longitude = coordinates[0]
    this.latitude = coordinates[1]
    this.setAddressViaReverseGeocode(this.longitude, this.latitude)
  }

  setAddressViaReverseGeocode(lng: number, lat: number) {
    this.mapboxService.getReverseGeocode(lng, lat).subscribe({
      next: (response: any) => {
        const placeName = response.features[0].place_name
        this.address = placeName.substring(0, placeName.indexOf(","))     // The field will be like this: { place_name: "Via Tal dei Tali 25, (...Other info we won't need...)" }
      },
      error: error => { }
    })
  }
}
