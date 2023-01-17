export interface Location {
  id: number
  region: string
  city: string

  // Optional fields
  address?: string
  longitude?: number
  latitude?: number
}
