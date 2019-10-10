package seedu.address.model.rule;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Rule's predicate in the form of an expression.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class RulePredicate {
    private final String value;

    /**
     * Constructs a {@code RulePredicate}.
     *
     * @param predicate A valid predicate.
     */
    public RulePredicate(String predicate) {
        requireNonNull(predicate);
        value = predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RulePredicate // instanceof handles nulls
                && value.equals(((RulePredicate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
