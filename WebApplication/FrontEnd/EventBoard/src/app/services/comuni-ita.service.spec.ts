import {TestBed} from '@angular/core/testing';

import {ComuniItaService} from './comuni-ita.service';

describe('ComuniItaService', () => {
  let service: ComuniItaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComuniItaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
