package budgetbuddy.model.rule;

/**
 * Represents a RulePredicate in a Rule with hidden implementation.
 * Guarantees: immutable;
 */
public abstract class RulePredicate {
    public abstract String getType();
}
