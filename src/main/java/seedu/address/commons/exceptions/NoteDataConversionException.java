package seedu.address.commons.exceptions;

/**
 * Represents an error during conversion of note data from JSON format.
 */
public class NoteDataConversionException extends DataConversionException {
    public NoteDataConversionException(Exception cause) {
        super(cause);
    }

}
