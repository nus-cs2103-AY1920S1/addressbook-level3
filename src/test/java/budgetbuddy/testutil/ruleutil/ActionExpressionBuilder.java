package budgetbuddy.testutil.ruleutil;

import budgetbuddy.model.rule.expression.ActionExpression;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.Value;

/**
 * A utility class to help with building ActionExpression objects.
 */
public class ActionExpressionBuilder {

    public static final Operator DEFAULT_OPERATOR = Operator.SET_CATEGORY;
    public static final Value DEFAULT_VALUE = new Value("Food");

    private Operator operator;
    private Value value;

    public ActionExpressionBuilder() {
        this.operator = DEFAULT_OPERATOR;
        this.value = DEFAULT_VALUE;
    }

    public ActionExpressionBuilder(ActionExpression toCopy) {
        this.operator = toCopy.getOperator();
        this.value = toCopy.getValue();
    }

    /**
     * Sets the {@code Operator} of the {@code ActionExpression} that we are building.
     */
    public ActionExpressionBuilder withOperator(String operator) {
        this.operator = Operator.of(operator);
        return this;
    }

    /**
     * Sets the {@code Value} of the {@code ActionExpression} that we are building.
     */
    public ActionExpressionBuilder withValue(String value) {
        this.value = new Value(value);
        return this;
    }

    public ActionExpression build() {
        return new ActionExpression(operator, value);
    }

}
