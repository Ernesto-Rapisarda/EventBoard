import {User} from "./user.model";

export class Event {
  public id: number
  public date: Date
  public title: string
  public price: number
  public urlPoster: string
  public eventType: string
  public position: string
  public description: string
  public organizerId: number
  public organizerFullName: string
  constructor() { }

  /*get Organizer() {
    return this._organizer
  }*/

}
