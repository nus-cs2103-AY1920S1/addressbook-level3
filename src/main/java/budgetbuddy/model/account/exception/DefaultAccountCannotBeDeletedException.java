package budgetbuddy.model.account.exception;

public class DefaultAccountCannotBeDeletedException extends RuntimeException {
    public DefaultAccountCannotBeDeletedException() {
        super("The default account must be changed to another account before this account can be deleted.");
    }

}
