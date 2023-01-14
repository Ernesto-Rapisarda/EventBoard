import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {RequestService} from "../../services/request.service";

@Component({
  selector: 'app-report-dialog',
  templateUrl: './report-dialog.component.html',
  styleUrls: ['./report-dialog.component.css']
})

export class ReportDialogComponent implements OnInit, OnDestroy{

  reportTypes: string[]
  operationConfirmed = false
  reportForm!: FormGroup;
  constructor(@Inject(MAT_DIALOG_DATA) protected data:  {
    type: string
    reason: string
  }, private dialogRef: MatDialogRef<ReportDialogComponent>, private requestService: RequestService) { }

  ngOnInit(): void {
    this.requestService.getReportTypes().subscribe({
      next: response => {
        this.reportTypes = response
        this.reportForm = new FormGroup<any>({
          type: new FormControl('', Validators.required),
          reason: new FormControl('', Validators.required)
        })
      },
      error: error => {}
    })
  }

  ngOnDestroy(): void {
    this.data.type = this.reportForm.value.type
    this.data.reason = this.reportForm.value.reason
  }

  onConfirm() {
    this.operationConfirmed = true
    this.dialogRef.close()
  }

  onCancel() {
    this.dialogRef.close()
  }
}
