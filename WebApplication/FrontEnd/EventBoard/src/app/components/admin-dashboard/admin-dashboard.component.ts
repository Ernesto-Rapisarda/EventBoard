import {Component, OnInit, ViewChild} from '@angular/core';
import {RequestService} from "../../services/request.service";
import {User} from "../../models/user.model";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {Report} from "../../models/report.model";
import {Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";

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




  constructor(private requestService: RequestService, private router: Router, protected authService: AuthService) {
    // Necessary to enable reloading
    this.router.routeReuseStrategy.shouldReuseRoute = () => {return false;};
  }

  ngOnInit(): void {
    this.fillTables()
  }

  fillTables() {
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
    this.requestService.promoteUser(id).subscribe({
      next: response => {
        alert("Operazione eseguita con successo. Ricarico la pagina")
        this.router.navigate(['/admin/dashboard'])
      },
      error: error => { }
    })
  }

  onBanUnban(id: number, reason: string) {
    let message = window.prompt('Inserisci la motivazione')
    this.requestService.banUser(id, message).subscribe({
      next: response => {
        alert("Operazione eseguita con successo. Ricarico la pagina")
        this.router.navigate(['/admin/dashboard'])
      },
      error: error => { console.log('ERRORE: ban') }
    })
  }
  onSolved(id: number) {
    console.log(id);
  }
}
