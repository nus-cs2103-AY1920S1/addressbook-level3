package seedu.eatme.testutil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.eatme.model.eatery.Review;

/**
 * Builds a review with the specified fields.
 */
public class ReviewBuilder {

    public static final String DEFAULT_DESCRIPTION = "Good chocolate ice-cream";
    public static final double DEFAULT_COST = 4.6;
    public static final int DEFAULT_RATING = 4;
    public static final String DEFAULT_DATE = "22/11/2019";


    private String reviewDescription;
    private double reviewCost;
    private int reviewRating;
    private Date reviewDate;

    public ReviewBuilder() throws ParseException {
        reviewDescription = DEFAULT_DESCRIPTION;
        reviewCost = DEFAULT_COST;
        reviewRating = DEFAULT_RATING;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        reviewDate = df.parse(DEFAULT_DATE);
    }

    /**
     * Initializes the ReviewBuilder with the data of {@code toCopy}.
     */
    public ReviewBuilder(Review toCopy) {
        reviewDescription = toCopy.getDescription();
        reviewCost = toCopy.getCost();
        reviewRating = toCopy.getRating();
        reviewDate = toCopy.getDate();
    }

    /**
     * Sets the {@code reviewDescription} of the {@code Review} that we are building.
     */
    public ReviewBuilder withDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
        return this;
    }


    /**
     * Sets the {@code reviewCost} of the {@code Review} that we are building.
     */
    public ReviewBuilder withCost(double reviewCost) {
        this.reviewCost = reviewCost;
        return this;
    }

    /**
     * Sets the {@code reviewRating} of the {@code Building} that we are building.
     */
    public ReviewBuilder withRating(int reviewRating) {
        this.reviewRating = reviewRating;
        return this;
    }

    /**
     * Sets the {@code reviewDate} of the {@code Review} that we are building.
     */
    public ReviewBuilder withDate(Date reviewDate) {
        this.reviewDate = reviewDate;
        return this;
    }

    public Review build() {
        return new Review(reviewDescription, reviewCost, reviewRating, reviewDate);
    }
}
