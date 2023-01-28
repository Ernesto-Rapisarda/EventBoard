import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {EventsService} from "../../services/events.service";
import {ComuniItaService} from "../../services/comuni-ita.service";
import {RequestService} from "../../services/request.service";
import {Event} from "../../models/event.model";

@Component({
  selector: 'app-search-date-location',
  templateUrl: './search-date-location.component.html',
  styleUrls: ['./search-date-location.component.css']
})
export class SearchDateLocationComponent implements OnInit {
  dateGroup!: FormGroup
  locationGroup!: FormGroup
  regions: String[]
  cities: String[]
  searchDone: boolean

  events: Array<Event>

  constructor(private eventsService: EventsService, private requestService: RequestService, private comuniItaService: ComuniItaService) {

  }

  ngOnInit(): void {
    this.events = []
    this.regions = []
    this.cities = []
    this.setRegions()
    this.cities = ['Qualsiasi']

    this.dateGroup = new FormGroup({
      startDate: new FormControl<Date | null>(null, Validators.required),
      endDate: new FormControl<Date | null>(null, Validators.required)
    });

    this.locationGroup = new FormGroup({
      region: new FormControl<string | null>(null, Validators.required),
      city: new FormControl<string | null>(null, Validators.required)
    })
  }

  protected setCities() {
    if(this.locationGroup.value.region !== 'Qualsiasi') {
      this.comuniItaService.getCities(this.locationGroup.value.region).subscribe({
        next: response => {
          this.cities = (response as string[])

          // Insert 'Qualsiasi' (any) as first element
          this.cities.unshift('Qualsiasi')

          // Necessary because when the user changes the region, the city field resets
          this.locationGroup.patchValue({city: 'Qualsiasi'})
        },
        error: error => { this.errorHandler(error) }
      })
    }
    else { this.cities = ['Qualsiasi'] }
  }

  onSearch() {
    this.events = []
    this.searchDone = true
    const searchType = "dateAndOrLocation"
    const initialRangeDate = this.dateGroup.value.startDate
    const finalRangeDate = this.dateGroup.value.endDate
    const region = this.locationGroup.value.region === 'Qualsiasi' ? null : this.locationGroup.value.region
    const city = this.locationGroup.value.city === 'Qualsiasi' ? null : this.locationGroup.value.city
    const title: any = null

    this.requestService.searchEvents(searchType, initialRangeDate, finalRangeDate, region, city, title).subscribe({
      next: response => {
        for(let element in response){
          let event: Event = {
            id: response[element].id,
            title: response[element].title,
            date: response[element].date,
            eventType: response[element].eventType,
            position: response[element].position,
            urlPoster: response[element].urlPoster,
            organizerFullName: response[element].organizer.toString(),
          }
          this.events.push(event)
        }
      }, error: error => { this.errorHandler(error) }
    })
  }

  private errorHandler(error: any) {
    // TODO: Implementare gestione errori
  }

  private setRegions() {
    this.comuniItaService.getRegions().subscribe({
      next: response => {
        this.regions = (response as string[])

        // Insert 'Qualsiasi' (any) as first element
        this.regions.unshift('Qualsiasi')

        // Necessary for initialization reasons
        this.locationGroup.patchValue({region: 'Qualsiasi', city: 'Qualsiasi'})
      },
      error: error => { this.errorHandler(error) }
    })
  }
}
