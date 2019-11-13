package budgetbuddy.testutil.scriptutil;

import budgetbuddy.model.script.Script;

/**
 * Typical scripts.
 */
public class TypicalScripts {
    public static final Script A = ScriptUtil.makeScript("A", "A description", "a code");
    public static final Script CHANGED_A = ScriptUtil.makeScript("A", "A description", "a different code");

    public static final Script B = ScriptUtil.makeScript("B", "B description", "b code");
    public static final Script C = ScriptUtil.makeScript("C", "C description", "c code");

    public static final Script LONG_NAME = ScriptUtil.makeScript("long_name", "Long description", "Long code");
    public static final Script LONG_DASH_NAME = ScriptUtil.makeScript("long_name", "Long- description", "Long- code");

    public static final Script UPPER_LONG_NAME = ScriptUtil.makeScript("LONG_NAME", "LONG description", "LONG code");

    private TypicalScripts() {}
}
