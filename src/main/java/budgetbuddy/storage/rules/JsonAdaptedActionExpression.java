package budgetbuddy.storage.rules;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.expression.ActionExpression;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.Value;

/**
 * Jackson-friendly version of {@link RuleAction}.
 */
public class JsonAdaptedActionExpression implements JsonAdaptedAction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Action's %s field is missing!";

    private final String operator;
    private final String value;

    /**
     * Constructs an expression {@code JsonAdaptedPredicateExpression} with the given action details.
     */
    @JsonCreator
    public JsonAdaptedActionExpression(@JsonProperty("operator") String operator,
                                       @JsonProperty("value") String value) {
        requireAllNonNull(operator, value);
        this.operator = operator;
        this.value = value;
    }

    /**
     * Converts a given {@code ActionExpression} into this class for Jackson use.
     * @param source
     */
    public JsonAdaptedActionExpression(ActionExpression source) {
        operator = source.getOperator().toString();
        value = source.getValue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted action object into the model's {@code ActionExpression} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted action.
     */
    @Override
    public ActionExpression toModelType() throws IllegalValueException {
        return new ActionExpression(getValidatedOperator(), getValidatedValue());
    }

    /**
     * Validates and converts the adapted operator into the model's {@code Operator} object.
     * @return The validated and converted operator.
     * @throws IllegalValueException If validation fails.
     */
    private Operator getValidatedOperator() throws IllegalValueException {
        if (operator == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Operator.class.getSimpleName()));
        }
        if (!Operator.isValidOperator(operator)) {
            throw new IllegalValueException(Operator.MESSAGE_CONSTRAINTS);
        }
        return Operator.of(operator);
    }

    /**
     * Validates and converts the adapted value into the model's {@code Value} object.
     * @return The validated and converted value.
     * @throws IllegalValueException If validation fails.
     */
    private Value getValidatedValue() throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Value.class.getSimpleName()));
        }
        if (!Value.isValidValue(value)) {
            throw new IllegalValueException(Value.MESSAGE_CONSTRAINTS);
        }
        return new Value(value);
    }
}
