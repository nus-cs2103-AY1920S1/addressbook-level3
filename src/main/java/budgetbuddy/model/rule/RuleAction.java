package budgetbuddy.model.rule;

/**
 * Represents a RuleAction in a Rule with hidden implementation.
 * Guarantees: immutable.
 */
public abstract class RuleAction {
    public abstract String getType();
}
