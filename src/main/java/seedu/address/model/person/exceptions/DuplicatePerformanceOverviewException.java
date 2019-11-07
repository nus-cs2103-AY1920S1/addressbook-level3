package seedu.address.model.person.exceptions;

public class DuplicatePerformanceOverviewException extends RuntimeException {
    public DuplicatePerformanceOverviewException() {
        super("Operation would result in duplicate performance overviews");
    }
}
