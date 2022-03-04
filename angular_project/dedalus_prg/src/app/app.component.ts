import {Component, ViewChild} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'dedalus_prg';
  value = '';
  displayedColumns = ['id', 'given', 'family', 'birthday'];
  dataSource: MatTableDataSource<any[]> = new MatTableDataSource<any[]>([]);

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatSort) sort: MatSort | undefined;

  constructor(private http: HttpClient) { }

  ngOnInit(){
    this.http.get<any>('http://localhost:8080/app/api/patient/getAllPatients').subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
    })
  }

  transferPatient(){
    if(this.value === ""){
      alert("Error: insert a value");
      return;
    }
    // @ts-ignore
    this.http.post<any>('http://localhost:8080/app/api/patient/transferPatient', { fullUrl: "http://hapi.fhir.org/baseR4/Patient/"+this.value }, {responseType: 'text'}).subscribe(data => {
      alert(JSON.stringify(data));
      this.value = "";
    })
  }

  ngAfterViewInit() {
    // @ts-ignore
    this.dataSource.paginator = this.paginator;
    // @ts-ignore
    this.dataSource.sort = this.sort;
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    // @ts-ignore
    this.dataSource.filter = filterValue;
  }


}

export interface UserData {
  id: string;
  name: string;
  progress: string;
  color: string;
}
