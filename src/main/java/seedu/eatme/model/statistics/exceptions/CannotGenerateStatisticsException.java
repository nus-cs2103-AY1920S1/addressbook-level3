package seedu.eatme.model.statistics.exceptions;

/**
 * Represents a statistics error that happens when there is an unknown error preventing the app from generating
 * statistics.
 */
public class CannotGenerateStatisticsException extends Exception {
    public CannotGenerateStatisticsException() {
        super("Unable to generate statistics due to unknown error.");
    }
}
