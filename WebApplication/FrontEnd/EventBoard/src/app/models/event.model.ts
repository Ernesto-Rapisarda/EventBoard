import {User} from "./user.model";

export class Event {
  public id: number
  public title: string
  public date: Date
  public urlPoster: string
  public eventType: string
  public position: string
  public location: string
  public description: string
  public organizer: string
  constructor() { }

  /*get Organizer() {
    return this._organizer
  }*/

}
