import { RatingForm } from "./RatingForm";

export default interface Category {
    id?: number | null,
    name: string,
    description: string,
    ratingForm: RatingForm,
    factors: string[],
  }