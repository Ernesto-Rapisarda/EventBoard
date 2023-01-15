import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {RequestService} from "../../services/request.service";

@Component({
  selector: 'app-report-dialog',
  templateUrl: './report-dialog.component.html',
  styleUrls: ['./report-dialog.component.css']
})

export class ReportDialogComponent implements OnInit{

  reportTypes: string[]
  operationConfirmed: boolean
  reportForm!: FormGroup;
  constructor(@Inject(MAT_DIALOG_DATA) protected data:  {
    type: string
    reason: string
    operationConfirmed: boolean
  }, private dialogRef: MatDialogRef<ReportDialogComponent>, private requestService: RequestService) { }

  ngOnInit(): void {
    this.reportForm = new FormGroup<any>({
      type: new FormControl('', Validators.required),
      reason: new FormControl('', Validators.required)
    })
    this.requestService.getReportTypes().subscribe({
      next: response => {
        this.reportTypes = response
      },
      error: error => {}
    })
  }

  onConfirm() {
    this.operationConfirmed = true
  }

  onCancel() {
    this.operationConfirmed = false
  }

  onSubmit() {
    this.data.type = this.reportForm.value.type
    this.data.reason = this.reportForm.value.reason
    this.data.operationConfirmed = this.operationConfirmed
    this.dialogRef.close(this.data)
  }
}
