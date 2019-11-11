package seedu.address.model.person.exceptions;

/**
 * Shows that rating given if out of the range of 5
 */
public class RatingException extends RuntimeException {
    public RatingException(String e) {
        super(e);
    }
}
