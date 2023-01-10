import { TestBed } from '@angular/core/testing';

import { ImgurApiService } from './imgur-api.service';

describe('ImgurApiService', () => {
  let service: ImgurApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImgurApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
