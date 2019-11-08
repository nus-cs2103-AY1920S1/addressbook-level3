package seedu.deliverymans.storage.restaurant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.deliverymans.model.restaurant.Rating;

/**
 * Jackson-friendly version of {@link Rating}.
 */
public class JsonAdaptedRating {

    private final int numberOfRatings;
    private final String rating;

    /**
     * Constructs a {@code JsonAdaptedRating} with the given {@code rating}.
     */
    @JsonCreator
    public JsonAdaptedRating(@JsonProperty("rating") String rating,
                           @JsonProperty("num") int numberOfRatings) {
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
    }

    /**
     * Converts a given {@code Rating} into this class for Jackson use.
     */
    public JsonAdaptedRating(Rating source) {
        numberOfRatings = source.numberOfRatings;
        rating = source.rating;
    }

    /**
     * Converts this Jackson-friendly adapted rating object into the model's {@code Rating} object.
     */
    public Rating toModelType() {
        return new Rating(rating, numberOfRatings);
    }
}
