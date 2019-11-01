package budgetbuddy.model.rule.script;

import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.script.ScriptName;

/**
 * Represents a RuleAction written as a script.
 * Guarantees: Field values are validated, immutable.
 */
public class ActionScript extends RuleAction {
    public static final String MESSAGE_CONSTRAINTS =
            "Action scripts should contain valid script names";

    private final ScriptName scriptName;

    /**
     * Constructs an {@code ActionScript}.
     *
     * @param scriptName the name of the script to be used.
     */
    public ActionScript(ScriptName scriptName) {
        this.scriptName = scriptName;
    }

    public ScriptName getScriptName() {
        return scriptName;
    }

    @Override
    public String getType() {
        return Rule.TYPE_SCRIPT;
    }

    /**
     * Returns true if both action scripts have the same identity and detail fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ActionScript)) {
            return false;
        }

        ActionScript otherAction = (ActionScript) other;
        return otherAction.getScriptName().equals(getScriptName());
    }

    @Override
    public int hashCode() {
        return scriptName.hashCode();
    }

    @Override
    public String toString() {
        return scriptName.toString();
    }
}
