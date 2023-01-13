import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BannerWelcomebackComponent } from './banner-welcomeback.component';

describe('BannerWelcomebackComponent', () => {
  let component: BannerWelcomebackComponent;
  let fixture: ComponentFixture<BannerWelcomebackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BannerWelcomebackComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BannerWelcomebackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
