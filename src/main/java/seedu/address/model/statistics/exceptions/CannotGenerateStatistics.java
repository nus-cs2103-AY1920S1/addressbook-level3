package seedu.address.model.statistics.exceptions;

/**
 * Represents a statistics error that happens when there is an unknown error preventing the app from generating
 * statistics.
 */
public class CannotGenerateStatistics extends Exception {
    public CannotGenerateStatistics() {
        super("Unable to generate statistics due to unknown error.");
    }
}
