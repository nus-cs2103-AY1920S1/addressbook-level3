package budgetbuddy.storage.rules;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.PredicateExpression;
import budgetbuddy.model.rule.expression.Value;

/**
 * Jackson-friendly version of {@link RulePredicate}.
 */
public class JsonAdaptedPredicateExpression implements JsonAdaptedPredicate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Predicate's %s field is missing!";

    private final String attribute;
    private final String operator;
    private final String value;

    /**
     * Constructs an expression {@code JsonAdaptedPredicateExpression} with the given predicate details.
     */
    @JsonCreator
    public JsonAdaptedPredicateExpression(@JsonProperty("attribute") String attribute,
                                          @JsonProperty("operator") String operator,
                                          @JsonProperty("value") String value) {
        requireAllNonNull(attribute, operator, value);
        this.attribute = attribute;
        this.operator = operator;
        this.value = value;
    }

    /**
     * Converts a given {@code PredicateExpression} into this class for Jackson use.
     * @param source
     */
    public JsonAdaptedPredicateExpression(PredicateExpression source) {
        attribute = source.getAttribute().toString();
        operator = source.getOperator().toString();
        value = source.getValue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted predicate object into the model's {@code PredicateExpression} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted predicate.
     */
    @Override
    public PredicateExpression toModelType() throws IllegalValueException {
        return new PredicateExpression(getValidatedAttribute(), getValidatedOperator(), getValidatedValue());
    }

    /**
     * Validates and converts the adapted attribute into the model's {@code Attribute} object.
     * @return The validated and converted attribute.
     * @throws IllegalValueException If validation fails.
     */
    private Attribute getValidatedAttribute() throws IllegalValueException {
        if (attribute == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Attribute.class.getSimpleName()));
        }
        if (!Attribute.isValidAttribute(attribute)) {
            throw new IllegalValueException(Attribute.MESSAGE_CONSTRAINTS);
        }
        return Attribute.of(attribute);
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
