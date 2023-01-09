import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-profile-edit-dialog',
  templateUrl: './profile-edit-dialog.component.html',
  styleUrls: ['./profile-edit-dialog.component.css']
})
export class ProfileEditDialogComponent implements OnInit, OnDestroy {
  constructor(@Inject(MAT_DIALOG_DATA) private data:  {
                                                        username: string,
                                                        name: string,
                                                        lastName: string,
                                                        email: string,
                                                        role: string
                                                      }, private dialogRef: MatDialogRef<ProfileEditDialogComponent>) { }

  ngOnInit(): void { }

  ngOnDestroy(): void {
    this.dialogRef.close(this.data)
  }

}
