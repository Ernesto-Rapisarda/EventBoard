import {Component, Input, OnInit} from '@angular/core';
import {ControlContainer, FormGroupDirective} from "@angular/forms";

@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.css'],
  viewProviders: [
    {
      provide: ControlContainer,
      useExisting: FormGroupDirective
    }
  ],
  providers: [ ]
})
export class DatepickerComponent implements OnInit {
  @Input() controlName: string
  minDate: Date
  maxDate: Date

  ngOnInit(): void {
    this.minDate = new Date()

    //Max date possible will be DEC 31 of next year (from current)
    this.maxDate = new Date(this.minDate.getFullYear()+1, 11, 31)

    console.log(this.minDate)
    console.log(this.maxDate)
  }
}
