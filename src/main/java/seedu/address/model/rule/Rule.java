package seedu.address.model.rule;

import java.util.Objects;

public class Rule {
    private final RulePredicate predicate;
    private final Actionable action;

    public Rule(RulePredicate predicate, Actionable action) {
        this.predicate = predicate;
        this.action = action;
    }

    public RulePredicate getPredicate() {
        return predicate;
    }

    public Actionable getAction() {
        return action;
    }

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
