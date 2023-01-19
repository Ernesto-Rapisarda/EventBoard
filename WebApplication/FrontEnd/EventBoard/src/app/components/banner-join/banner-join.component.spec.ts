import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BannerJoinComponent} from './banner-join.component';

describe('BannerJoinComponent', () => {
  let component: BannerJoinComponent;
  let fixture: ComponentFixture<BannerJoinComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BannerJoinComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BannerJoinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
