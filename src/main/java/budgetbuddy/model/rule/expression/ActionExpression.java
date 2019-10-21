package budgetbuddy.model.rule.expression;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.regex.Pattern;

import budgetbuddy.model.rule.RuleAction;

/**
 * Represents a RuleAction written as an expression.
 * Guarantees: operator and value are present and not null, field values are validated, immutable.
 */
public class ActionExpression extends RuleAction {

    public static final String MESSAGE_CONSTRAINTS =
            "Expressions should contain exactly two terms in the order <operator> <value> "
            + "and should not be blank";

    public static final String MESSAGE_TYPE_REQUIREMENTS =
            "The operator and value of the expression have to evaluate to the correct type: "
            + "e.g. setcategory food, where 'setcategory' expects a string , "
            + "and 'food' is a string";

    public static final Pattern FORMAT_REGEX =
            Pattern.compile("^(?<exprOperator>\\S+)\\s(?<exprValue>.*)$");

    private final Operator operator;
    private final Value value;

    /**
     * Constructs an {@code ActionExpression}.
     *
     * @param operator the operator used in the expression.
     * @param value the value used in the expression.
     */
    public ActionExpression(Operator operator, Value value) {
        requireAllNonNull(operator, value);
        this.operator = operator;
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public Value getValue() {
        return value;
    }

    /**
     * Returns true if both action expressions have the same identity and detail fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ActionExpression)) {
            return false;
        }

        ActionExpression otherAction = (ActionExpression) other;
        return otherAction.getOperator().equals(getOperator())
                && otherAction.getValue().equals(getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, value);
    }

    @Override
    public String toString() {
        return String.format("%s %s", operator, value);
    }
}
