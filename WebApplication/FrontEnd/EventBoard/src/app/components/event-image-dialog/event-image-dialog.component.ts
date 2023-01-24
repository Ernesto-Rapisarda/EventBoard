import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-event-image-dialog',
  templateUrl: './event-image-dialog.component.html',
  styleUrls: ['./event-image-dialog.component.css']
})
export class EventImageDialogComponent {
  image: string
  constructor(@Inject(MAT_DIALOG_DATA) protected data:  {
    image: string
  }, private dialogRef: MatDialogRef<EventImageDialogComponent>) { this.image = data.image }

}
