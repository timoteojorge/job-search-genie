import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { JobService } from './job.service';
import { Page } from '../model/page';
import { Job } from '../model/job';

const page: Page<Job> =
{
  content: [
    {
      description: 'Job description',
      id: '1538919817',
      title: 'Job Title',
    }]
};

describe('JobService', () => {
  let service: JobService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(JobService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getJobsByCityAndCountryCode() should return data', () => {
    service.getJobsByCityAndCountryCode('london', 'gb', 0).subscribe((res: Page<Job>) => {
      expect(res.content[0].description).toEqual('Job description');
    });

    const req = httpMock.expectOne('http://localhost:8080/jobsearch/api/v1/jobs?cityName=london&countryCode=gb&page=0&size=20');
    expect(req.request.method).toBe('GET');
    req.flush(page);
  });


});
