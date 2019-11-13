package budgetbuddy.model.rule.script;

import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.script.ScriptName;

/**
 * Represents a RulePredicate written as a script.
 * Guarantees: Field values are validated, immutable.
 */
public class PredicateScript extends RulePredicate {
    public static final String MESSAGE_CONSTRAINTS =
            "Predicate scripts should contain valid script names";

    private final ScriptName scriptName;

    /**
     * Constructs an {@code PredicateScript}.
     *
     * @param scriptName the name of the script to be used.
     */
    public PredicateScript(ScriptName scriptName) {
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
     * Returns true if both predicate scripts have the same identity and detail fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PredicateScript)) {
            return false;
        }

        PredicateScript otherPredicate = (PredicateScript) other;
        return otherPredicate.getScriptName().equals(getScriptName());
    }

    @Override
    public int hashCode() {
        return scriptName.hashCode();
    }

    @Override
    public String toString() {
        return String.format("run script %s", scriptName.toString());
    }
}
