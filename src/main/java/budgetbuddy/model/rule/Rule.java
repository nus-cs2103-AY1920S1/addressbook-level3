package budgetbuddy.model.rule;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Rule in budget buddy.
 * Guarantees: predicate and action are present and not null, field values are validated, immutable.
 */
public class Rule {
    public static final String TYPE_EXPRESSION = "EXPRESSION";
    public static final String TYPE_SCRIPT = "SCRIPT";

    // rule details
    private final RulePredicate predicate;
    private final RuleAction action;

    /**
     * Every field must be present and not null.
     */
    public Rule(RulePredicate predicate, RuleAction action) {
        requireAllNonNull(predicate, action);
        this.predicate = predicate;
        this.action = action;
    }

    public RulePredicate getPredicate() {
        return predicate;
    }

    public RuleAction getAction() {
        return action;
    }

    /**
     * Returns true if both rules have the same identity and detail fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Rule)) {
            return false;
        }

        Rule otherRule = (Rule) other;
        return otherRule.getPredicate().equals(getPredicate())
                && otherRule.getAction().equals(getAction());
    }

    @Override
    public int hashCode() {
        return Objects.hash(predicate, action);
    }

    @Override
    public String toString() {
        return String.format("If: %s, then: %s.", predicate, action);
    }
}
