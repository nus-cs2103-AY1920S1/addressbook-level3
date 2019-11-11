package organice.model.person;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.AppUtil.checkArgument;

/**
 * Represents the status of donor and patient in ORGANice.
 * By default, all newly added donor and patient will be not processing.
 * When a donor and patient is matched, they will be processed and their status will be processing.
 * When they are done with cross-matching, their status will change to either done or not processing,
 * depending on the result of the cross-matching.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS = "Status can only be processing, not processing or done";

    /*
     * The first character of the status must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public static final String STATUS_PROCESSING = "processing";
    public static final String STATUS_NOT_PROCESSING = "not processing";
    public static final String STATUS_DONE = "done";


    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        value = status.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX)
                && (test.toLowerCase().equals(STATUS_PROCESSING) || test.toLowerCase().equals(STATUS_NOT_PROCESSING)
                || test.toLowerCase().equals(STATUS_DONE));
    }

    /**
     * Checks if the {@code Status} is processing
     * @return boolean if this Status value is 'processing'
     */
    public boolean isProcessing() {
        return value.toLowerCase().equals(STATUS_PROCESSING);
    }

    /**
     * Checks if the {@code Status} is not processing.
     * @return boolean if this Status value is 'not processing'
     */
    public boolean isNotProcessing() {
        return value.toLowerCase().equals(STATUS_NOT_PROCESSING);
    }

    /**
     * Checks if the {@code Status} is done.
     * @return boolean if this Status value is 'done'
     */
    public boolean isDone() {
        return value.toLowerCase().equals(STATUS_DONE);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
