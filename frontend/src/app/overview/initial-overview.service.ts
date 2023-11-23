import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LapsUpdateDTO} from "../types/lapse-update.type";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class InitialOverviewService {

  constructor(private http: HttpClient) {
  }

  getInitialOverview(): Observable<LapsUpdateDTO[]> {
    return this.http.get<LapsUpdateDTO[]>('/currenttimings')
  }
}
