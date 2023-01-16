import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-comment-edit-dialog',
  templateUrl: './comment-edit-dialog.component.html',
  styleUrls: ['./comment-edit-dialog.component.css']
})
export class CommentEditDialogComponent implements OnInit {
  text: string
  originalText: string

  constructor(@Inject(MAT_DIALOG_DATA) protected data:  {
    text: string,
    operationConfirmed: boolean
  }, private dialogRef: MatDialogRef<CommentEditDialogComponent>) { }

  ngOnInit(): void {
    this.originalText = this.text = this.data.text
  }

  onConfirm() {
    this.data.text = this.text
    this.data.operationConfirmed = true
    this.dialogRef.close(this.data)
  }

  onCancel() {
    this.dialogRef.close(this.data)
  }
}
