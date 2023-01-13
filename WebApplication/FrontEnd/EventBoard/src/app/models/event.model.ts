import {Review} from "./review.model";
import {Participation} from "./partecipation.model";
import {Comment} from "./comment.model";
import {Like} from "./like.model";

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
  likeList?: Like[]
  participationList?: Participation[]
  reviewList?: Review[]
}
