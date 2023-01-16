import {Component, Input} from '@angular/core';
import {Report} from "../../models/report.model";

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})

export class ReportComponent {

  @Input() report: Report

  constructor() {}

}
