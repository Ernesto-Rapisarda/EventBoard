import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchDateLocationComponent } from './search-date-location.component';

describe('SearchDateLocationComponent', () => {
  let component: SearchDateLocationComponent;
  let fixture: ComponentFixture<SearchDateLocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchDateLocationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchDateLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
