import {Component, Inject, NgZone, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {take} from "rxjs/operators";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";

@Component({
  selector: 'app-review-edit-dialog',
  templateUrl: './review-edit-dialog.component.html',
  styleUrls: ['./review-edit-dialog.component.css']
})
export class ReviewEditDialogComponent implements OnInit {
  private readonly MIN = 1;
  private readonly MAX = 5;

  rating: number
  text: string
  originalRating: number
  originalText: string

  @ViewChild('autosize') autosize: CdkTextareaAutosize;

  constructor(@Inject(MAT_DIALOG_DATA) protected data:  {
    text: string,
    rating: number
    operationConfirmed: boolean
  }, private dialogRef: MatDialogRef<ReviewEditDialogComponent>, private _ngZone: NgZone) { }

  ngOnInit(): void {
    this.originalText = this.text = this.data.text
    this.originalRating = this.rating = this.data.rating
  }

  updateRating() {
    if (++this.rating > this.MAX)
      this.rating = this.MIN;
  }

  onConfirm() {
    this.data.text = this.text
    this.data.rating = this.rating
    this.data.operationConfirmed = true
    this.dialogRef.close(this.data)
  }

  onCancel() {
    this.dialogRef.close(this.data)
  }

  triggerResize() {
    // Wait for changes to be applied, then trigger textarea resize.
    this._ngZone.onStable.pipe(take(1)).subscribe(() => this.autosize.resizeToFitContent(true));
  }
}
