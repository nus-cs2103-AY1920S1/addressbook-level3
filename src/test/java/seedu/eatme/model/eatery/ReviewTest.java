package seedu.eatme.model.eatery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.testutil.Assert.assertThrows;
import static seedu.eatme.testutil.ReviewBuilder.DEFAULT_COST;
import static seedu.eatme.testutil.ReviewBuilder.DEFAULT_DATE;
import static seedu.eatme.testutil.ReviewBuilder.DEFAULT_DESCRIPTION;
import static seedu.eatme.testutil.ReviewBuilder.DEFAULT_RATING;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import seedu.eatme.testutil.ReviewBuilder;

public class ReviewTest {

    private static final String REVIEW_DESCRIPTION = "good";
    private static final double REVIEW_COST = 4.2;
    private static final int REVIEW_RATING = 3;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Review(null, 0, 0, null));
    }

    @Test
    public void constructorInvalidReviewCost_throwsIllegalArgumentException() {
        double invalidCost = -2;
        DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        assertThrows(IllegalArgumentException.class, () ->
                new Review(REVIEW_DESCRIPTION, invalidCost, 0, df.parse(DEFAULT_DATE)));
    }

    @Test
    public void constructorInvalidReviewRating_throwsIllegalArgumentException() {
        int invalidRating = 6;
        DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        assertThrows(IllegalArgumentException.class, () ->
                new Review(REVIEW_DESCRIPTION, 0, invalidRating, df.parse(DEFAULT_DATE)));
    }

    @Test
    public void isValidReviewDescription() {
        //null description
        assertThrows(NullPointerException.class, () -> Review.isValidDescription(null));

        assertFalse(Review.isValidDescription(" "));

        //all reviews other than blank space are valid as anything can be written in reviews
        assertTrue(Review.isValidDescription("good"));
        assertTrue(Review.isValidDescription("GOOD"));
        assertTrue(Review.isValidDescription("@bad"));
        assertTrue(Review.isValidDescription("1 for 1"));
    }

    @Test
    public void isValidReviewCost() {
        //less than 0
        assertFalse(Review.isValidCost(-2));

        //any positive double
        assertTrue(Review.isValidCost(0));
        assertTrue(Review.isValidCost(3.2));
        assertTrue(Review.isValidCost(9999));
    }

    @Test
    public void isValidReviewRating() {
        //double, < 0 and > 5
        assertFalse(Review.isValidRating("-2"));
        assertFalse(Review.isValidRating("1.2"));
        assertFalse(Review.isValidRating("6"));

        //integers between 0 and 5
        assertTrue(Review.isValidRating("0"));
        assertTrue(Review.isValidRating("3"));
        assertTrue(Review.isValidRating("5"));
    }

    @Test
    public void equals() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Review review = new Review(DEFAULT_DESCRIPTION, DEFAULT_COST, DEFAULT_RATING, df.parse(DEFAULT_DATE));

        //same values -> returns true
        Review reviewCopy = new ReviewBuilder(review).build();
        assertTrue(review.equals(reviewCopy));

        //same object -> returns true
        assertTrue(review.equals(review));

        //null -> returns false
        assertFalse(review.equals(null));

        //different type -> returns false
        assertFalse(review.equals(2));

        //different description -> returns false
        Review editedReviewDescription = new ReviewBuilder(review).withDescription(REVIEW_DESCRIPTION).build();
        assertFalse(editedReviewDescription.equals(review));

        //different cost -> returns false
        Review editedReviewCost = new ReviewBuilder(review).withCost(REVIEW_COST).build();
        assertFalse(editedReviewCost.equals(review));

        //different rating -> returns false
        Review editedReviewRating = new ReviewBuilder(review).withRating(REVIEW_RATING).build();
        assertFalse(editedReviewRating.equals(review));
    }
}
