import {Component, Input, OnInit} from '@angular/core';
import {Report} from "../../models/report.model";
import {RequestService} from "../../services/request.service";
import {Comment} from "../../models/comment.model";

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})

export class ReportComponent implements OnInit {

  @Input() report: Report

  constructor() {}

  ngOnInit(): void { }

}
