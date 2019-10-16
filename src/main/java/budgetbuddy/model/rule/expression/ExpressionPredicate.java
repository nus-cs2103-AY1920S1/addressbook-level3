package budgetbuddy.model.rule.expression;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.regex.Pattern;

import budgetbuddy.model.rule.RulePredicate;

/**
 * Represents a RulePredicate written as an expression.
 * Guarantees: operator and params are present and not null, field values are validated, immutable.
 */
public class ExpressionPredicate extends RulePredicate {

    public static final String MESSAGE_CONSTRAINTS =
            "Expressions should only contain three terms in the order <attribute> <operator> <value> "
            + "and should not be blank";

    public static final String MESSAGE_TYPE_REQUIREMENTS =
            "The attribute, operator, and value of the expression have to evaluate to the correct type: "
            + "e.g. amount < 5, where 'amount' evaluates to a long integer, "
            + "'<' expects a long integer, and '5' is a long integer";

    public static final Pattern FORMAT_REGEX =
            Pattern.compile("^(?<exprAttribute>\\S+)\\s+(?<exprOperator>\\S+)\\s(?<exprValue>.*)$");

    private final Attribute attribute;
    private final Operator operator;
    private final Value value;

    /**
     * Constructs an {@code ExpressionPredicate}.
     *
     * @param attribute the attribute used in the expression.
     * @param operator the operator used in the expression.
     * @param value the value used in the expression.
     */
    public ExpressionPredicate(Attribute attribute, Operator operator, Value value) {
        requireAllNonNull(attribute, operator, value);
        this.attribute = attribute;
        this.operator = operator;
        this.value = value;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public Operator getOperator() {
        return operator;
    }

    public Value getValue() {
        return value;
    }

    /**
     * Returns true if both expression predicates have the same identity and detail fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExpressionPredicate)) {
            return false;
        }

        ExpressionPredicate otherPredicate = (ExpressionPredicate) other;
        return otherPredicate.getAttribute().equals(getAttribute())
                && otherPredicate.getOperator().equals(getOperator())
                && otherPredicate.getValue().equals(getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, operator, value);
    }
}
