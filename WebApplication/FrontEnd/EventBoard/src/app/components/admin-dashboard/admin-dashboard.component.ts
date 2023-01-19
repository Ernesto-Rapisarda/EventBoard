import {Component, OnInit, ViewChild} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {User} from "../../models/user.model";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {Report} from "../../models/report.model";

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  displayedColumnsUser: string[] = ["id", "username", "name", "lastName", "email", "role", "action"]
  user: any
  userList: User[]
  userDataSource: any

  displayedColumnsReport: string[] = ["id", "person", "type", "message", "date", "status", "action"]
  report: any
  reportList: Report[];
  reportDataSource: any

  @ViewChild('userPaginator' , {static: true}) userPaginator: MatPaginator;
  @ViewChild('reportPaginator' , {static: true}) reportPaginator: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;




  constructor(private requestService: RequestService) { }

  ngOnInit(): void {
    this.onTest() // TODO: cambiare questo nome, per cortesia
  }

  onTest() {

    /* Users */
    this.requestService.getUsers().subscribe({
      next: response => {
        console.log(response)
        this.userList = response
        this.userDataSource = new MatTableDataSource<User>(this.userList)
        this.userDataSource.paginator = this.userPaginator
        this.userDataSource.sort = this.sort
      },
      error: error => { console.log('ERRORE: users') }
    });

    /* Reports */
    this.requestService.getReports().subscribe({
        next: response => {
          console.log(response)
          this.reportList = response
          this.reportDataSource = new MatTableDataSource<Report>(this.reportList)
          this.reportDataSource.paginator = this.reportPaginator
          this.reportDataSource.sort = this.sort
        },
        error: error => { console.log('ERRORE: reports') }
      });
  }

  /* TODO */
  onPromote(id: number) {
    console.log(id)
  }

  onBanUnban(id: number, reason: string) {

    this.requestService.banUser(id, reason).subscribe({
      next: response => {
        console.log("BAN/UNBAN: " + response )
      },
      error: error => { console.log('ERRORE: ban') }
    })
  }
  onSolved(id: number) {
    console.log(id);
  }
}
