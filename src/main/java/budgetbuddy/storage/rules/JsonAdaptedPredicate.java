package budgetbuddy.storage.rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.rule.RulePredicate;

/**
 * Jackson-friendly version of {@link RulePredicate}.
 */
@JsonTypeInfo(use = Id.NAME, property = "type")
@JsonSubTypes({
        @Type(value = JsonAdaptedPredicateExpression.class, name = "expr"),
})
public interface JsonAdaptedPredicate {
    /**
     * Converts this Jackson-friendly adapted predicate object into the model's {@code RulePredicate} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted predicate.
     */
    RulePredicate toModelType() throws IllegalValueException;
}
