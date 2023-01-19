import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-review-edit-dialog',
  templateUrl: './review-edit-dialog.component.html',
  styleUrls: ['./review-edit-dialog.component.css']
})
export class ReviewEditDialogComponent implements OnInit {
  private readonly MIN = 1;
  private readonly MAX = 10;

  rating: number
  text: string
  originalRating: number
  originalText: string

  constructor(@Inject(MAT_DIALOG_DATA) protected data:  {
    text: string,
    rating: number
    operationConfirmed: boolean
  }, private dialogRef: MatDialogRef<ReviewEditDialogComponent>) { }

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
}
