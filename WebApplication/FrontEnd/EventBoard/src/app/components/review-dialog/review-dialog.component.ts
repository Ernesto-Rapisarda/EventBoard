import { Component } from '@angular/core';

@Component({
  selector: 'app-review-dialog',
  templateUrl: './review-dialog.component.html',
  styleUrls: ['./review-dialog.component.css']
})
export class ReviewDialogComponent {

  constructor() {
    this.reportTypes = ['default', 'opt1', 'opt2', 'opt3']
  }

  reportTypes: string[]

}
