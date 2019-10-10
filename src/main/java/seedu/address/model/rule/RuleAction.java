package seedu.address.model.rule;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Rule's action.
 * Guarantees: immutable.
 */
public class RuleAction {
    private final String value;

    /**
     * Constructs a {@code RuleAction}.
     *
     * @param action A valid action.
     */
    public RuleAction(String action) {
        requireNonNull(action);
        value = action;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RuleAction // instanceof handles nulls
                && value.equals(((RuleAction) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
