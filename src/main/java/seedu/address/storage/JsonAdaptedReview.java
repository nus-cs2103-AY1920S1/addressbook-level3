package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.eatery.Review;

/**
 * Jackson friendly version of {@code Review}
 */
class JsonAdaptedReview {

    private final String description;
    private final double cost;
    private final int rating;

    @JsonCreator
    public JsonAdaptedReview(@JsonProperty("description") String description,
                             @JsonProperty("cost") double cost,
                             @JsonProperty("rating") int rating) {

        this.description = description;
        this.cost = cost;
        this.rating = rating;
    }

    public JsonAdaptedReview(Review review) {
        description = review.getDescription();
        cost = review.getCost();
        rating = review.getRating();
    }

    /**
     * Converts this Jackson-friendly adapted review object into the model's {@code Review} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted review.
     */
    public Review toModelType() throws IllegalValueException {
        if (!Review.isValidDescription(description)) {
            throw new IllegalValueException(Review.REVIEW_CONSTRAINTS);
        }

        if (!Review.isValidCost(cost)) {
            throw new IllegalValueException(Review.REVIEW_CONSTRAINTS);
        }

        if (!Review.isValidRating(String.valueOf(rating))) {
            throw new IllegalValueException((Review.REVIEW_CONSTRAINTS));
        }

        return new Review(description, cost, rating);
    }
}
