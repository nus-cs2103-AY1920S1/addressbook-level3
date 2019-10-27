package budgetbuddy.logic.rules.testable;

import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.rules.RuleProcessingUtil;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a less-than expression.
 */
public class LessThanExpression extends TestableExpression {
    /**
     * Constructs a LessThanExpression with the given attribute and value.
     *
     * @param attribute the attribute to be tested with.
     * @param value the value to be tested against.
     */
    public LessThanExpression(Attribute attribute, Value value) {
        super(attribute, value);
    }

    @Override
    public boolean test(Transaction txn) {
        requireNonNull(txn);
        double left = (double) RuleProcessingUtil.extractAttribute(attribute, txn);
        double right = Double.parseDouble(value.toString());
        return left >= 0 && left < right;
    }
}
