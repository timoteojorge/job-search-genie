import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { City } from '../model/city';
import { Page } from '../model/page';

@Injectable({
  providedIn: 'root'
})
export class CityService {

  constructor(private httpClient: HttpClient) { }

  getCitiesByPrefix(cityPrefix: string, page: number, size: number = 5): Observable<Page<City>> {
    let url = `http://${window.location.hostname}:8080/jobsearch/api/v1/cities?cityPrefix=${cityPrefix}&page=${page}&size=${size}`;
    return this.httpClient.get<Page<City>>(url);
  }
}
