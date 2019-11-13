package budgetbuddy.storage.rules;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.script.ActionScript;
import budgetbuddy.model.script.ScriptName;

/**
 * Jackson-friendly version of {@link RuleAction}.
 */
public class JsonAdaptedActionScript implements JsonAdaptedAction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Script name is missing!";

    private final String scriptName;

    /**
     * Constructs a {@code JsonAdaptedActionScript} with the given action details.
     */
    @JsonCreator
    public JsonAdaptedActionScript(@JsonProperty("scriptName") String scriptName) {
        requireNonNull(scriptName);
        this.scriptName = scriptName;
    }

    /**
     * Converts a given {@code ActionScript} into this class for Jackson use.
     */
    public JsonAdaptedActionScript(ActionScript source) {
        scriptName = source.getScriptName().toString();
    }

    /**
     * Converts this Jackson-friendly adapted action object into the model's {@code ActionScript} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted action.
     */
    @Override
    public ActionScript toModelType() throws IllegalValueException {
        return new ActionScript(getValidatedScriptName());
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
