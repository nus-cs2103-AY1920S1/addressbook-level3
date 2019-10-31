package budgetbuddy.logic.rules.testable;

import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.rules.RuleEngine;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a more-than-or-equal-to expression.
 */
public class MoreEqualExpression extends TestableExpression {
    /**
     * Constructs a MoreEqualExpression with the given attribute and value.
     *
     * @param attribute the attribute to be tested with.
     * @param value the value to be tested against.
     */
    public MoreEqualExpression(Attribute attribute, Value value) {
        super(attribute, value);
    }

    @Override
    public boolean test(Transaction txn) {
        requireNonNull(txn);
        double left = (double) RuleEngine.extractAttribute(attribute, txn);
        double right = Double.parseDouble(value.toString());
        return left >= 0 && left >= right;
    }
}
