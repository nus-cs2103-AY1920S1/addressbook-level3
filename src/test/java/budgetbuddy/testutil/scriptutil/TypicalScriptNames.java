package budgetbuddy.testutil.scriptutil;

import budgetbuddy.model.script.ScriptName;

/**
 * Typical script names.
 */
public class TypicalScriptNames {
    public static final ScriptName A = new ScriptName("A");
    public static final ScriptName B = new ScriptName("B");
    public static final ScriptName C = new ScriptName("C");

    public static final ScriptName LONG_NAME = new ScriptName("long_name");
    public static final ScriptName LONG_DASH_NAME = new ScriptName("long-name");

    public static final ScriptName UPPER_LONG_NAME = new ScriptName("LONG_NAME");

    private TypicalScriptNames() {}
}
