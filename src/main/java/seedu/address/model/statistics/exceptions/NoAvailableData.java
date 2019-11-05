package seedu.address.model.statistics.exceptions;

/**
 * Represents a statistics error that happens when there is no data to generate any form of statistics.
 */
public class NoAvailableData extends Exception {

    public NoAvailableData() {
        super("Unable to generate statistics due to unknown error.");
    }
}
