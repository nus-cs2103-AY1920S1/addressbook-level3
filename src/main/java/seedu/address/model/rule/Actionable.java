package seedu.address.model.rule;

/**
 * Represents an action with hidden internal logic and the ability to be performed.
 */
public interface Actionable {
    /**
     * Performs the action.
     */
    void perform();
}
