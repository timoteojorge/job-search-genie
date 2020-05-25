import { TestBed, async, ComponentFixture, tick, fakeAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { JobService } from './job-service/job.service';
import { CityService } from './city-service/city.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subscription, of } from 'rxjs';
import { Page } from './model/page';
import { Job } from './model/job';
import { City } from './model/city';

describe('AppComponent', () => {

  let fixture: ComponentFixture<AppComponent>;
  let appComponent: AppComponent;
  let jobService: JobService;
  let cityService: CityService;


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        ReactiveFormsModule,
        FormsModule,
        HttpClientTestingModule,
        MatAutocompleteModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        JobService,
        CityService
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    appComponent = fixture.componentInstance;
    jobService = fixture.debugElement.injector.get(JobService);
    cityService = fixture.debugElement.injector.get(CityService);
    appComponent.citiesSubscription = new Subscription();
    appComponent.jobsSubscription = new Subscription();
    fixture.detectChanges();
  });

  it('should create the app', () => {
    expect(appComponent).toBeTruthy();
  });

  it('should render title', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('.toolbar span').textContent).toContain('Job Search Genie');
  });

  it('should init variables and call searchCity on ngOnInit', () => {
    const searchCitySpy = spyOn(appComponent, 'searchCity').and.callThrough();
    appComponent.ngOnInit();
    expect(appComponent.currentPage).toBe(0);
    expect(appComponent.totalPages).toBe(0);
    expect(appComponent.formGroup).toBeTruthy();
    expect(searchCitySpy).toHaveBeenCalled();
  });

  it('should increment currentPage and call changeCity on more()', () => {
    const changeCitySpy = spyOn(appComponent, 'changeCity').and.callThrough();
    appComponent.currentPage = 0;
    appComponent.totalPages = 2;
    appComponent.more();
    expect(changeCitySpy).toHaveBeenCalled();
  });

  it('should call jobService.getJobsByCityAndCountryCode and call changeCity on more()', () => {
    let page = new Page<Job>();
    page.content = [new Job()];
    appComponent.currentPage = 0;
    const getJobsByCityAndCountryCodeSpy = spyOn(jobService, 'getJobsByCityAndCountryCode').and.returnValue(of(page));
    appComponent.changeCity(new City());
    expect(getJobsByCityAndCountryCodeSpy).toHaveBeenCalled();
    expect(appComponent.jobs.length).toBe(1);
  });

  it('should call cityService.getCitiesByPrefix on city value change', fakeAsync(() => {
    let page = new Page<City>();
    page.content = [new City()];
    const getCitiesByPrefixSpy = spyOn(cityService, 'getCitiesByPrefix').and.returnValue(of(page));
    appComponent.formGroup.get('city').setValue('city');
    tick(1100);
    expect(getCitiesByPrefixSpy).toHaveBeenCalled();
    expect(appComponent.cityNotFound).toBeFalsy();
    expect(appComponent.cities.length).toBe(1);
  }));
});
