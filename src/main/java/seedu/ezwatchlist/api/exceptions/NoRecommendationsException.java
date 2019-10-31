package seedu.ezwatchlist.api.exceptions;

/**
 * Represents an error when RecommendationEngine fails to return a recommendation.
 */
public class NoRecommendationsException extends Exception {
    public NoRecommendationsException(String message) {
        super(message);
    }
}
