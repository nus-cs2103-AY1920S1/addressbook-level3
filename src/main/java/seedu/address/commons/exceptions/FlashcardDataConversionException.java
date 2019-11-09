package seedu.address.commons.exceptions;

/**
 * Represents an error during conversion of flashcard data from JSON format.
 */
public class FlashcardDataConversionException extends DataConversionException {
    public FlashcardDataConversionException(Exception cause) {
        super(cause);
    }

}
