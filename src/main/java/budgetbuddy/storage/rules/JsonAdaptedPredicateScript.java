package budgetbuddy.storage.rules;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.rule.script.PredicateScript;
import budgetbuddy.model.script.ScriptName;

/**
 * Jackson-friendly version of {@link RulePredicate}.
 */
public class JsonAdaptedPredicateScript implements JsonAdaptedPredicate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Script name is missing!";

    private final String scriptName;

    /**
     * Constructs a {@code JsonAdaptedPredicateScript} with the given predicate details.
     */
    @JsonCreator
    public JsonAdaptedPredicateScript(@JsonProperty("scriptName") String scriptName) {
        requireNonNull(scriptName);
        this.scriptName = scriptName;
    }

    /**
     * Converts a given {@code PredicateScript} into this class for Jackson use.
     */
    public JsonAdaptedPredicateScript(PredicateScript source) {
        scriptName = source.getScriptName().toString();
    }

    /**
     * Converts this Jackson-friendly adapted predicate object into the model's {@code PredicateScript} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted predicate.
     */
    @Override
    public PredicateScript toModelType() throws IllegalValueException {
        return new PredicateScript(getValidatedScriptName());
    }

    /**
     * Validates and converts the adapted script name into the model's {@code ScriptName} object.
     * @return The validated and converted script name.
     * @throws IllegalValueException If validation fails.
     */
    private ScriptName getValidatedScriptName() throws IllegalValueException {
        if (scriptName == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        if (!ScriptName.isValidName(scriptName)) {
            throw new IllegalValueException(ScriptName.MESSAGE_CONSTRAINTS);
        }
        return new ScriptName(scriptName);
    }
}
