import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ReviewEditDialogComponent} from './review-edit-dialog.component';

describe('ReviewEditDialogComponent', () => {
  let component: ReviewEditDialogComponent;
  let fixture: ComponentFixture<ReviewEditDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReviewEditDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
