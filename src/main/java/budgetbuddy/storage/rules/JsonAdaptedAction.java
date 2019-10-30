package budgetbuddy.storage.rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.rule.RuleAction;

/**
 * Jackson-friendly version of {@link RuleAction}.
 */

@JsonTypeInfo(use = Id.NAME, property = "type")
@JsonSubTypes({
        @Type(value = JsonAdaptedActionExpression.class, name = "expr"),
})
public interface JsonAdaptedAction {
    /**
     * Converts this Jackson-friendly adapted action object into the model's {@code RuleAction} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted action.
     */
    RuleAction toModelType() throws IllegalValueException;
}
