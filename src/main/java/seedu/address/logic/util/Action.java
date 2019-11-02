package seedu.address.logic.util;

/**
 * Represents an Action to transfer information from Logic to Ui.
 */
public abstract class Action {

    /**
     * Runs the action.
     * @return boolean true if successful
     */
    public abstract boolean action();

    /**
     * Gets String representation of action for display.
     * @return String value of action
     */
    public abstract String toString();

}
