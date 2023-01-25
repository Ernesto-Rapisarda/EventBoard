import {Component, Inject, NgZone, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {take} from "rxjs/operators";

@Component({
  selector: 'app-comment-edit-dialog',
  templateUrl: './comment-edit-dialog.component.html',
  styleUrls: ['./comment-edit-dialog.component.css']
})
export class CommentEditDialogComponent implements OnInit {
  text: string
  originalText: string
  @ViewChild('autosize') autosize: CdkTextareaAutosize;
  constructor(@Inject(MAT_DIALOG_DATA) protected data:  {
    text: string,
    operationConfirmed: boolean
  }, private dialogRef: MatDialogRef<CommentEditDialogComponent>, private _ngZone: NgZone) { }

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

  triggerResize() {
    // Wait for changes to be applied, then trigger textarea resize.
    this._ngZone.onStable.pipe(take(1)).subscribe(() => this.autosize.resizeToFitContent(true));
  }
}
