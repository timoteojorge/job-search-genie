import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, finalize, startWith, tap } from "rxjs/operators";
import { CityService } from './city-service/city.service';
import { JobService } from './job-service/job.service';
import { City } from './model/city';
import { Job } from './model/job';
import { Page } from './model/page';


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return !!(control && control.invalid && (control.dirty || control.touched));
  }
}
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  formGroup: FormGroup;
  cities: any[];
  citiesSubscription: Subscription;
  jobsSubscription: Subscription;
  cityNotFound: boolean = false;
  loadingCities: boolean;
  loadingJobs: boolean;
  jobs: Job[];
  currentPage: number;
  totalPages: number;
  noJobsFound: boolean = false;
  citiesCurrentPage: number = 0;

  constructor(private fb: FormBuilder,
    private cityService: CityService,
    private jobService: JobService) { }

  ngOnInit() {
    this.formGroup = this.fb.group({
      city: new FormControl("")
    });
    this.currentPage = 0;
    this.totalPages = 0;
    this.searchCity();
  }

  showCityName(city: City): string {
    return city ? `${city.name},${city.countryCode}` : '';
  }

  searchCity() {
    this.citiesSubscription = this.formGroup
      .get("city")
      .valueChanges.pipe(
        startWith(null),
        debounceTime(1000),
        distinctUntilChanged(),
        filter(cityPrefix => cityPrefix && cityPrefix.length > 2),
        tap(cityPrefix => {
          this.loadingCities = true;
          this.cities = [];
          this.cityService
            .getCitiesByPrefix(cityPrefix, this.citiesCurrentPage)
            .pipe()
            .toPromise()
            .then((res: Page<City>) => {
              this.loadingCities = false;
              if (!res || !res.content || res.content.length === 0) {
                this.cityNotFound = true;
                this.cities = [];
                return;
              }
              this.cityNotFound = false;
              this.cities = res.content;
            });
        })
      )
      .subscribe();
  }

  changeCity(city: City) {
    this.loadingJobs = true;
    this.jobsSubscription = this.jobService.getJobsByCityAndCountryCode(city.name, city.countryCode, this.currentPage)
      .pipe(finalize(() => this.loadingJobs = false))
      .subscribe((res: Page<Job>) => {
        if(res && res.content && res.content.length > 0){
          this.noJobsFound = false;
          if (this.currentPage === 0) {
            this.jobs = res.content;
          } else {
            this.jobs = this.jobs.concat(res.content);
          }
          this.totalPages = res.totalPages;
        } else {
          this.noJobsFound = true;
          this.jobs = [];
        }
      },
      () => {
        this.jobs = [];
        this.totalPages = 0;
        this.currentPage = 0;
        this.noJobsFound = true;
      });
  }

  more() {
    if (this.currentPage + 1 >= this.totalPages) return;
    this.currentPage++;
    this.changeCity(this.formGroup.get('city').value);
  }

  ngOnDestroy() {
    this.citiesSubscription.unsubscribe();
    this.jobsSubscription.unsubscribe();
  }
}