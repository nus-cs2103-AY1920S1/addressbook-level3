package seedu.address.model.eatery;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.Objects;

/**
 * Represents an Eatery's review in the EatMe application.
 */
public class Review {

    public static final String REVIEW_CONSTRAINTS = "Review description should not be empty,"
            + " Cost cannot be negative and cannot exceed 10000 and"
            + " Rating should be an integer between 0 and 5.";

    /**
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "[^\\s].*";

    /**
     * Validate rating by converting t0 String and checking
     * if it has only one digit between 0 and 5.
     */
    public static final String RATING_VALIDATION_INDEX = "[0-5]";

    private final String description;
    private final double cost;
    private final int rating;
    private final Date date;

    /**
     * Constructs a {@code Review}
     *
     * @param description A valid description.
     * @param cost Cost of the meal being reviewed.
     * @param rating Rating out of 5 for the meal being reviewed.
     */
    public Review(String description, double cost, int rating, Date date) {
        requireAllNonNull(description, cost, rating, date);
        checkArgument(isValidReview(description, cost, rating), REVIEW_CONSTRAINTS);
        this.description = description;
        this.cost = cost;
        this.rating = rating;
        this.date = date;
    }

    /**
     * Returns true if the given review is a valid review.
     */
    public static boolean isValidReview(String description, double cost, int rating) {
        return isValidDescription(description) && isValidCost(cost)
                && isValidRating(String.valueOf(rating));
    }

    /**
     * Returns true if the given review description is valid.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    /**
     * Returns true if the given cost is valid.
     */
    public static boolean isValidCost(double test) {
        return (test >= 0 && test < 10000);
    }

    /**
     * Returns true if the given rating is valid.
     */
    public static boolean isValidRating(String test) {
        return test.matches(RATING_VALIDATION_INDEX);
    }

    public Date getDate() {
        return this.date;
    }

    public String getDescription() {
        return this.description;
    }

    public double getCost() {
        return this.cost;
    }

    public int getRating() {
        return this.rating;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Price: ")
                .append(getCost())
                .append(" Rating: ")
                .append(getRating())
                .append(" Date: ")
                .append(getDate().toString());
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Review)) {
            return false;
        }

        Review otherReview = (Review) other;
        return otherReview.getDescription().equals(getDescription())
                && otherReview.getCost() == getCost()
                && otherReview.getRating() == getRating()
                && otherReview.getDate().equals(getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, cost, rating, date);
    }

}
