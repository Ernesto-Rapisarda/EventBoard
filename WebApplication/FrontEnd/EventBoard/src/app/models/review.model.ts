export interface Review {
  date: Date
  message: string
  rating: number
  person: number      // id of the user who wrote it
  event: number
  namePerson: string
}
