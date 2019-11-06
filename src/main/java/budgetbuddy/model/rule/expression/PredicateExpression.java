package budgetbuddy.model.rule.expression;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.rules.RuleEngine.convertValue;

import java.util.Objects;
import java.util.regex.Pattern;

import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RulePredicate;

/**
 * Represents a RulePredicate written as an expression.
 * Guarantees: attribute, operator and value are present and not null, field values are validated, immutable.
 */
public class PredicateExpression extends RulePredicate {

    public static final String MESSAGE_CONSTRAINTS =
            "Predicate expressions should contain exactly three terms in the order\n"
            + "<attribute> <operator> <value> "
            + "and should not be blank";

    public static final String MESSAGE_TYPE_REQUIREMENTS =
            "The attribute, operator, and value of the expression have to evaluate to the correct type:\n"
            + "e.g. inamt < 5, where 'inamt' and '5' both evaluate to the same type, a numerical value,\n"
            + "and '<' expects comparable arguments.";

    public static final Pattern FORMAT_REGEX =
            Pattern.compile("^(?<exprAttribute>\\S+)\\s+(?<exprOperator>\\S+)\\s(?<exprValue>.*)$");

    private final Attribute attribute;
    private final Operator operator;
    private final Value value;

    /**
     * Constructs an {@code PredicateExpression}.
     *
     * @param attribute the attribute used in the expression.
     * @param operator the operator used in the expression.
     * @param value the value used in the expression.
     */
    public PredicateExpression(Attribute attribute, Operator operator, Value value) {
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
     * Returns if the predicate expression is valid,
     * i.e. attribute and value are all working with the expected type specified by the operator.
     */
    public static boolean isValidPredicateExpr(Attribute attribute, Operator operator, Value value) {
        return operator.getExpectedTypes()
                .stream()
                .anyMatch(type -> {
                    System.out.println("TEST: " + type);
                    try {
                        convertValue(type, value);
                        System.out.println(type);
                        return type.equals(attribute.getEvaluatedType());
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                        return false;
                    }
                });
    }

    @Override
    public String getType() {
        return Rule.TYPE_EXPRESSION;
    }

    /**
     * Returns true if both predicate expressions have the same identity and detail fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PredicateExpression)) {
            return false;
        }

        PredicateExpression otherPredicate = (PredicateExpression) other;
        return otherPredicate.getAttribute().equals(getAttribute())
                && otherPredicate.getOperator().equals(getOperator())
                && otherPredicate.getValue().equals(getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, operator, value);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", attribute, operator, value);
    }
}
