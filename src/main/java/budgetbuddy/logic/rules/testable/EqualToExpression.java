package budgetbuddy.logic.rules.testable;

import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.rules.RuleEngine;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a equal-to expression.
 */
public class EqualToExpression extends TestableExpression {
    /**
     * Constructs a EqualToExpression with the given attribute and value.
     *
     * @param attribute the attribute to be tested with.
     * @param value the value to be tested against.
     */
    public EqualToExpression(Attribute attribute, Value value) {
        super(attribute, value);
    }

    @Override
    public boolean test(Transaction txn, Account account) {
        requireNonNull(txn);
        double left = (double) RuleEngine.extractAttribute(attribute, txn);
        double right = Double.parseDouble(value.toString());
        return left == right;
    }
}
