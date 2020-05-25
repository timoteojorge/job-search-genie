import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SearchJobsResult } from '../model/job-search-result';
import { Page } from '../model/page';
import { Job } from '../model/job';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  constructor(private httpClient: HttpClient) { }
  
  getJobsByCityAndCountryCode(cityName: string, countryCode: string, page: number, size: number = 20): Observable<Page<Job>> {
    let url = `http://${window.location.hostname}:8080/jobsearch/api/v1/jobs?cityName=${cityName}&countryCode=${countryCode}&page=${page}&size=${size}`;
    return this.httpClient.get<Page<Job>>(url);
  }
}
