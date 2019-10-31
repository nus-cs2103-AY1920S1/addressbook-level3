package budgetbuddy.model.account.exceptions;

/**
 * Signals that the default account cannot be deleted.
 */
public class LastAccountCannotBeDeletedException extends RuntimeException {
    public LastAccountCannotBeDeletedException() {
        super("The last account cannot be deleted.");
    }
}
