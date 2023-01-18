import {Component, OnInit, ViewChild} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {User} from "../../models/user.model";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  report: any
  reportList: any;
  displayedColumnsUser: string[] = ["id", "username", "name", "lastName", "email", "role", "action"]
  user: any
  userList: User[]

  userDataSource: any
  reportDataSource: any

  @ViewChild(MatPaginator) paginator!: MatPaginator
  @ViewChild(MatSort) sort!: MatSort

  constructor(private requestService: RequestService) { }

  ngOnInit(): void {
    this.onTest()
  }

  onTest() {

    /* Users */
    this.requestService.getUsers().subscribe({
      next: response => {
        console.log(response)
        this.userList = response
        this.userDataSource = new MatTableDataSource<User>(this.userList)
        this.userDataSource.paginator = this.paginator
        this.userDataSource.sort = this.sort
      },
      error: error => { console.log('ERRORE: users') }
    });

    /* Reports */
    this.requestService.getReports().subscribe({
        next: response => {
          console.log(response)
          this.reportList = response
        },
        error: error => { console.log('ERRORE: reports') }
      });
  }

  public getUserList() {
    return this.userList;
  }

  onPromote(id: number) {
    console.log(id)
  }

  onBanUnban(id: number) {
    console.log(id)
  }
}
