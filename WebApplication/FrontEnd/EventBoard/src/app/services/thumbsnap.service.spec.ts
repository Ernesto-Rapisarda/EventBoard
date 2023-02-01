import { TestBed } from '@angular/core/testing';

import { ThumbsnapService } from './thumbsnap.service';

describe('ThumbsnapService', () => {
  let service: ThumbsnapService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ThumbsnapService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
