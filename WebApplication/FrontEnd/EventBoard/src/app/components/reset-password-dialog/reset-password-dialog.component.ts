import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-reset-password-dialog',
  templateUrl: './reset-password-dialog.component.html',
  styleUrls: ['./reset-password-dialog.component.css']
})
export class ResetPasswordDialogComponent implements OnInit {
  resetPasswordForm!: FormGroup

  constructor(@Inject(MAT_DIALOG_DATA) protected data:{
    operationConfirmed: boolean
    username: string
  }, private dialogRef: MatDialogRef<ResetPasswordDialogComponent>) { }

  ngOnInit(): void {
    this.resetPasswordForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(16) ])
    });
  }

  onSubmit() {
    this.data.operationConfirmed = true
    this.data.username = this.resetPasswordForm.value.username
    this.dialogRef.close(this.data)
  }

  onCancel() {
    this.dialogRef.close()
  }
}
