package seedu.address.logic.script;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import seedu.address.logic.script.exceptions.ScriptException;
import seedu.address.model.Model;

/**
 * Evaluates scripts on a model.
 */
public class ScriptManager {
    private static final ScriptEngine scriptEngine;

    static {
        scriptEngine = new ScriptEngineManager().getEngineByExtension("js");
        if (scriptEngine == null) {
            throw new IllegalStateException("Could not instantiate JavaScript engine");
        }
    }

    /**
     * Is a private constructor for a static-only class.
     */
    private ScriptManager() { }

    /**
     * Evaluate a script on the given model.
     * @param script the script
     * @param model the model
     * @return the result of the script, which may be <code>null</code>
     * @throws ScriptException if an exception occurs during script evaluation
     */
    public static Object evaluateScript(String script, Model model) throws ScriptException {
        scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE).put("ab", model);
        try {
            return scriptEngine.eval(script);
        } catch (Exception ex) {
            throw new ScriptException(String.format("Exception while evaluating script: %1$s", ex.toString()), ex);
        }
    }
}
