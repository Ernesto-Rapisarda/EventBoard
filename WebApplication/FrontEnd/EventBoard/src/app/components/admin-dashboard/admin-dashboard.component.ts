import {Component, OnInit} from '@angular/core';
import {RequestService} from "../../services/request.service";

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  constructor(private requestService: RequestService) { }

  ngOnInit(): void {
        this.onTest()
    }

  onTest() {
    this.requestService.getReports().subscribe({
        next: response => { console.log(response) },
        error: error => { console.log('error') }
      });
  }

}
