package seedu.eatme.model.statistics.exceptions;

/**
 * Represents a statistics error that happens when there is no data to generate any form of statistics.
 */
public class NoAvailableDataException extends Exception {
    public NoAvailableDataException() {
        super("Unable to generate statistics due to unknown error.");
    }
}
