package seedu.address.model.person;

/**
 * Represents Driver rating
 */
public class DriverRating {

    public static final String RATING_OUT_OF_RANGE = "Rating exceeds maximum rating of 5";

    private int totalRating;
    private int totalNoOfReviews;

    public DriverRating() {
        this.totalRating = 0;
        this.totalNoOfReviews = 0;
    }

    public void setRating(int rating, int totalNoOfReviews) {
        this.totalRating = rating * totalNoOfReviews;
        this.totalNoOfReviews = totalNoOfReviews;
    }

    public int getTotalNoOfReviews() {
        return this.totalNoOfReviews;
    }

    /**
     * Retrieves an overall rating
     * @return an int derived from totalRating divided by totalNoOfReviews
     */
    public int getRating() {
        if (this.totalNoOfReviews == 0) {
            return 0;
        }
        return this.totalRating / this.totalNoOfReviews;
    }

    /**
     * Adds a new rating
     * @param rating in int
     */
    public void addRating(int rating) {
        this.totalRating += rating;
        this.totalNoOfReviews += 1;
    }

    /**
     *Checks if rating is within the range of 1-5;
     * @param rating in int
     */
    public static boolean isValid(int rating) {
        if (rating > 5 || rating < 1) {
            return false;
        }
        return true;
    }

    /**
     * Returns Driver Rating in String form
     * @return Rating in string
     */
    public String toString() {
        if (this.totalNoOfReviews == 0) {
            return String.valueOf(0);
        }
        return String.format("%.2s", (double) this.totalRating / this.totalNoOfReviews);
    }

}
