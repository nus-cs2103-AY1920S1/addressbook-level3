package seedu.address.model.rule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Rule in budget buddy.
 * Guarantees: predicate and action are present and not null, field values are validated, immutable.
 */
public class Rule {

    // rule details
    private final RulePredicate predicate;
    private final Actionable action;

    /**
     * Every field must be present and not null.
     */
    public Rule(RulePredicate predicate, Actionable action) {
        requireAllNonNull(predicate, action);
        this.predicate = predicate;
        this.action = action;
    }

    public RulePredicate getPredicate() {
        return predicate;
    }

    public Actionable getAction() {
        return action;
    }

    /**
     * Returns true if both rules have the same identity or detail fields.
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
}
