package seedu.address.commons.exceptions;

import seedu.address.AlfredException;

/**
 * Represents an error during conversion of data from one format to another.
 */
public class DataConversionException extends AlfredException {
    /** Constructs an instance of {@code DataConversionException}.
     *
     * @param cause Can have one of two main causes.
     * 1) IOException from error reading from JSON file
     * 2) Illegal value exception, thrown when there are illegal values in json file
     */
    public DataConversionException(Exception cause) {
        super(cause);
    }

}
