package budgetbuddy.logic.rules.testable;

import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.rules.RuleProcessingUtil;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

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
    public boolean test(Transaction txn) {
        requireNonNull(txn);
        String left = (String) RuleProcessingUtil.extractAttribute(attribute, txn);
        String right = value.toString();
        return left.contains(right);
    }
}
