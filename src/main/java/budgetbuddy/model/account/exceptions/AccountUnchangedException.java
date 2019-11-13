package budgetbuddy.model.account.exceptions;

/**
 * Signals that the account trying to be edited would remain unchanged after editing.
 */
public class AccountUnchangedException extends RuntimeException {
    public AccountUnchangedException() {
        super("Operation would not change the account.");
    }
}
