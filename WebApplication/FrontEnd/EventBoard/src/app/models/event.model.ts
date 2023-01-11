import {Review} from "./review.model";
import {Partecipation} from "./partecipation.model";

export interface Event {
  id: number
  date: Date
  title: string
  price?: number
  soldOut?: boolean
  urlPoster: string
  description?: string
  eventType?: string
  position?: number
  organizer?: number      // organizer id
  organizerFullName: string
  commentList?: Comment[]
  participationList?: Partecipation[]
  reviewList?: Review[]
}
