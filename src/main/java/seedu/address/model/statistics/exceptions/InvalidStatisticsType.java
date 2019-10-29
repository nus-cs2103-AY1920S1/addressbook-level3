package seedu.address.model.statistics.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a statistics error.
 */
public class InvalidStatisticsType extends IllegalValueException {

    public InvalidStatisticsType(String message) {
        super(message);
    }
}
