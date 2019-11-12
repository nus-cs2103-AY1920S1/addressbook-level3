package seedu.address.commons.exceptions;

/**
 * Represents an error during conversion of cheatsheet data from JSON format.
 */
public class CheatSheetDataConversionException extends DataConversionException {
    public CheatSheetDataConversionException(Exception cause) {
        super(cause);
    }

}
