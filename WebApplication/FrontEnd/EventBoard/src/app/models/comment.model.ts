export interface Comment {
  id: number
  date: Date
  message: string
  event: number         // event id
  person: number        // organizer id
}
