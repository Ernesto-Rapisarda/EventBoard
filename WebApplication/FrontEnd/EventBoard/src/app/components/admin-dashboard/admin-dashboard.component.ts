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



  report: any
  reportList: any;

  user: any
  userList: any

  onTest() {

    /* Users */
    this.requestService.getUsers().subscribe({
      next: response => {
        console.log(response)
        this.userList = response
      },
      error: error => { console.log('ERRORE: users') }
    })

    /* Reports */
    this.requestService.getReports().subscribe({
        next: response => {
          console.log(response)
          this.reportList = response
        },
        error: error => { console.log('ERRORE: reports') }
      })
  }

}
