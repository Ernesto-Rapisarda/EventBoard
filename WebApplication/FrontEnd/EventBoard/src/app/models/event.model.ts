import {User} from "./user.model";

export class Event {
  constructor(
    public id: number,
    public name: string,
    public date: Date,
    public imageUrl: string,
    public location: string,
    public description: string,
    private _organizer: User,
  ) {
  }

  get Organizer() {
    return this._organizer
  }

}
