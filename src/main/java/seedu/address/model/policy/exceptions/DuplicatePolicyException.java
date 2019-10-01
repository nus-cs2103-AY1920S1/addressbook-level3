package seedu.address.model.policy.exceptions;

/**
 * Signals that the operation will result in duplicate Policies (Policies are considered duplicates if they have the same
 * name).
 */
public class DuplicatePolicyException extends RuntimeException {
    public DuplicatePolicyException() {
        super("Operation would result in duplicate policies");
    }
}
