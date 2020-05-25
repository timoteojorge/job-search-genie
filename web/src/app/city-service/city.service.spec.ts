import { TestBed } from '@angular/core/testing';

import { CityService } from './city.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { City } from '../model/city';
import { Page } from '../model/page';

const page: Page<City> =
{
  content: [
    {
      name: 'London',
      countryCode: 'GB',
    }]
};

describe('CityService', () => {
  let service: CityService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule]
    });
    service = TestBed.inject(CityService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getCitiesByPrefix() should return data', () => {
    service.getCitiesByPrefix('london', 0).subscribe((res: Page<City>) => {
      expect(res.content[0].name).toEqual('London');
    });

    const req = httpMock.expectOne('http://localhost:8080/jobsearch/api/v1/cities?cityPrefix=london&page=0&size=5');
    expect(req.request.method).toBe('GET');
    req.flush(page);
  });
});
