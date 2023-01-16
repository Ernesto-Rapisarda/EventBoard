import {Preference} from "./preference.model";

export interface User {
  id: number,
  name: string,
  lastName: string,
  username: string,
  email: string,
  role: string,
  token: string,
  preferences: Preference[]
}
