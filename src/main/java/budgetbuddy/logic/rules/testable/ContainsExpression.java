package budgetbuddy.logic.rules.testable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.rules.RuleEngine;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Value;

/**
 * Represents a contains expression.
 */
public class ContainsExpression extends TestableExpression {
    /**
     * Constructs a ContainsExpression with the given attribute and value.
     *
     * @param attribute the attribute to be tested with.
     * @param value the value to be tested against.
     */
    public ContainsExpression(Attribute attribute, Value value) {
        super(attribute, value);
    }

    @Override
    public boolean test(Index txnIndex, Account account) {
        requireAllNonNull(txnIndex, account);
        String left = RuleEngine.extractAttribute(attribute, txnIndex, account).toString();
        String right = value.toString();
        return left.contains(right);
    }
}
