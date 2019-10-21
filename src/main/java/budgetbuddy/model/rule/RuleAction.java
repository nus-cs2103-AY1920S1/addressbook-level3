package budgetbuddy.model.rule;

/**
 * Represents a RuleAction in a Rule with hidden implementation.
 * Guarantees: immutable.
 */
public abstract class RuleAction {
    public static final String MESSAGE_CONSTRAINTS =
            "Actions should be either expressions or scripts "
                    + "and should not be blank";
}
