import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LocationChooserDialogComponent} from './location-chooser-dialog.component';

describe('LocationChooserDialogComponent', () => {
  let component: LocationChooserDialogComponent;
  let fixture: ComponentFixture<LocationChooserDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LocationChooserDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LocationChooserDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
