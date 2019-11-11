package seedu.address.model.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the action a command word should execute.
 * Guarantees: immutable; is valid as declared in {@link #isValidAction(String)}
 */
public class CommandAction {

    public static final String MESSAGE_CONSTRAINTS =
            "Not a valid action!";

    /**
     * The first character of the action must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String action;

    /**
     * Constructs a {@code CommandAction}.
     *
     * @param action A valid action.
     */
    public CommandAction(String action) {
        requireNonNull(action);
        checkArgument(isValidAction(action), MESSAGE_CONSTRAINTS);
        this.action = action;
    }

    /**
     * Returns a defensive copy of the {@code CommandAction}.
     */
    public CommandAction copy() {
        CommandAction copiedCommandAction = new CommandAction(this.toString());
        return copiedCommandAction;
    }

    /**
     * Returns true if a given string is a valid action.
     */
    public static boolean isValidAction(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return action;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommandAction // instanceof handles nulls
                && action.equals(((CommandAction) other).action)); // state check
    }

    @Override
    public int hashCode() {
        return action.hashCode();
    }

}
