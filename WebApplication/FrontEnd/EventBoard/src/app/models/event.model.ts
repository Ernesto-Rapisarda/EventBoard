import {Review} from "./review.model";
import {Participation} from "./partecipation.model";
import {Comment} from "./comment.model";
import {Like} from "./like.model";
import {Location} from "./location.model";

export interface Event {
  id: number
  date: Date
  title: string
  urlPoster: string
  organizerFullName: string

  // Optional fields
  price?: number
  soldOut?: boolean
  description?: string
  eventType?: string
  position?: Location                   // See location.model.ts for info about this field
  organizer?: number
  urlTicket?: string
  commentList?: Comment[]               // See comment.model.ts for info about this field
  likeList?: Like[]                     // See like.model.ts for info about this field
  participationList?: Participation[]   // See participation.model.ts for info about this field
  reviewList?: Review[]                 // See review.model.ts for info about this field
}
