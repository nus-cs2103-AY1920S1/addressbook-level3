package seedu.address.logic.rules;

import seedu.address.model.rule.expression.Attribute;
import seedu.address.model.rule.expression.Value;
import seedu.address.model.transaction.Transaction;

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
        long left = (long) RuleProcessingUtil.extractAttribute(attribute, txn);
        long right = Long.parseLong(value.toString());
        return left < right;
    }
}
