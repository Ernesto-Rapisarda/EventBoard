import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventImageDialogComponent } from './event-image-dialog.component';

describe('EventImageDialogComponent', () => {
  let component: EventImageDialogComponent;
  let fixture: ComponentFixture<EventImageDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EventImageDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventImageDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
