import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SimilarEventsBoxComponent} from './similar-events-box.component';

describe('SimilarEventsBoxComponent', () => {
  let component: SimilarEventsBoxComponent;
  let fixture: ComponentFixture<SimilarEventsBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SimilarEventsBoxComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SimilarEventsBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
