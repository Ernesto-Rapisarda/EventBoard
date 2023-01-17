import {Preference} from "./preference.model";
import {Location} from "./location.model";

export interface User {
  id: number,
  name: string,
  lastName: string,
  username: string,
  email: string,
  role: string,
  token: string,
  preferences: Preference[]   // See preference.model.ts for info about this field
  location: Location          // See location.model.ts for info about this field
}
