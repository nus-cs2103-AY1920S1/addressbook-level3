package budgetbuddy.testutil.scriptutil;

import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.script.Script;
import budgetbuddy.model.script.ScriptName;

/**
 * Helper methods for script tests.
 */
public class ScriptUtil {
    private ScriptUtil() {}

    /**
     * Makes a script with the given arguments.
     */
    public static Script makeScript(String name, String description, String code) {
        return new Script(new ScriptName(name), new Description(description), code);
    }
}
