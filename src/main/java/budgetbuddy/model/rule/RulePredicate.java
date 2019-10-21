package budgetbuddy.model.rule;

/**
 * Represents a RulePredicate in a Rule with hidden implementation.
 * Guarantees: immutable;
 */
public abstract class RulePredicate {
    public static final String MESSAGE_CONSTRAINTS =
            "Predicates should be either expressions or scripts "
                    + "and should not be blank";
}
