package budgetbuddy.logic.rules.testable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.logic.rules.RuleEngine;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Value;

/**
 * Represents a more-than expression.
 */
public class MoreThanExpression extends TestableExpression {
    /**
     * Constructs a MoreThanExpression with the given attribute and value.
     *
     * @param attribute the attribute to be tested with.
     * @param value the value to be tested against.
     */
    public MoreThanExpression(Attribute attribute, Value value) {
        super(attribute, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean test(Index txnIndex, Account account) {
        requireAllNonNull(txnIndex, account);
        Comparable left = (Comparable) RuleEngine.extractAttribute(attribute, txnIndex, account);
        Comparable right;
        try {
            right = (Comparable) RuleEngine.convertValue(attribute.getEvaluatedType(), value);

            // attributes should never have a blank type
            assert right != null;
        } catch (ParseException e) {
            return false;
        }

        return left != null && left.compareTo(right) > 0;
    }
}
