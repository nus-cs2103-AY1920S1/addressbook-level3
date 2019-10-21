package seedu.address.logic.util;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Action {

    /**
     * Runs the intended action.
     */
    public abstract boolean action();

    public abstract String toString();

}
