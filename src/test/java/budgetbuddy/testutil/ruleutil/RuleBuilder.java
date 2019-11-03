package budgetbuddy.testutil.ruleutil;

import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.RulePredicate;

/**
 * A utility class to help with building Rule objects.
 */
public class RuleBuilder {

    public static final RulePredicate DEFAULT_PREDICATE = TypicalPredicates.DESC_CONTAINS_FOOD;
    public static final RuleAction DEFAULT_ACTION = TypicalActions.SET_CAT_FOOD;

    private RulePredicate predicate;
    private RuleAction action;

    public RuleBuilder() {
        this.predicate = DEFAULT_PREDICATE;
        this.action = DEFAULT_ACTION;
    }

    public RuleBuilder(Rule toCopy) {
        this.predicate = toCopy.getPredicate();
        this.action = toCopy.getAction();
    }

    /**
     * Sets the {@code RulePredicate} of the {@code Rule} that we are building.
     */
    public RuleBuilder withPredicate(RulePredicate predicate) {
        this.predicate = predicate;
        return this;
    }

    /**
     * Sets the {@code RuleAction} of the {@code Rule} that we are building.
     */
    public RuleBuilder withAction(RuleAction action) {
        this.action = action;
        return this;
    }

    public Rule build() {
        return new Rule(predicate, action);
    }

}
