package budgetbuddy.testutil.ruleutil;

import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.PredicateExpression;
import budgetbuddy.model.rule.expression.Value;

/**
 * A utility class to help with building PredicateExpression objects.
 */
public class PredicateExpressionBuilder {

    public static final Attribute DEFAULT_ATTRIBUTE = Attribute.DESCRIPTION;
    public static final Operator DEFAULT_OPERATOR = Operator.CONTAINS;
    public static final Value DEFAULT_VALUE = new Value("food");

    private Attribute attribute;
    private Operator operator;
    private Value value;

    public PredicateExpressionBuilder() {
        this.attribute = DEFAULT_ATTRIBUTE;
        this.operator = DEFAULT_OPERATOR;
        this.value = DEFAULT_VALUE;
    }

    public PredicateExpressionBuilder(PredicateExpression toCopy) {
        this.attribute = toCopy.getAttribute();
        this.operator = toCopy.getOperator();
        this.value = toCopy.getValue();
    }

    /**
     * Sets the {@code Attribute} of the {@code PredicateExpression} that we are building.
     */
    public PredicateExpressionBuilder withAttribute(String attribute) {
        this.attribute = Attribute.of(attribute);
        return this;
    }

    /**
     * Sets the {@code Operator} of the {@code PredicateExpression} that we are building.
     */
    public PredicateExpressionBuilder withOperator(String operator) {
        this.operator = Operator.of(operator);
        return this;
    }

    /**
     * Sets the {@code Value} of the {@code PredicateExpression} that we are building.
     */
    public PredicateExpressionBuilder withValue(String value) {
        this.value = new Value(value);
        return this;
    }

    public PredicateExpression build() {
        return new PredicateExpression(attribute, operator, value);
    }

}
