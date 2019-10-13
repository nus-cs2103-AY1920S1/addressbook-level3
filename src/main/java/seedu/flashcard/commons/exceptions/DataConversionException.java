package seedu.flashcard.commons.exceptions;

/**
 * Represent an error while converting data from one format to another.
 */
public class DataConversionException extends Exception{

    public DataConversionException(Exception cause) {
        super(cause);
    }
}
