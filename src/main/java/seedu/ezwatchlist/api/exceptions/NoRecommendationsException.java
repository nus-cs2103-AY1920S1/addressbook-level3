package seedu.ezwatchlist.api.exceptions;

/**
 * Represents an error when RecommendationEngine fails to return a recommendation.
 */
public class NoRecommendationsException extends Exception {
    private String message;

    public NoRecommendationsException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
