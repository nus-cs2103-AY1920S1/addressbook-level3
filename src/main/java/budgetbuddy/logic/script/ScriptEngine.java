package budgetbuddy.logic.script;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineManager;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.logic.script.exceptions.ScriptException;
import budgetbuddy.model.script.Script;

/**
 * Evaluates scripts.
 */
public class ScriptEngine {
    private final Logger logger = LogsCenter.getLogger(ScriptEngine.class);

    private final Object scriptEngineLock;
    private final ScriptEnvironmentInitialiser initialiser;
    private final javax.script.ScriptEngine scriptEngine;

    /**
     * Creates a new ScriptManager with the specified environment initialiser.
     *
     * @param initialiser The environment initialiser.
     */
    public ScriptEngine(ScriptEnvironmentInitialiser initialiser) {
        this.initialiser = initialiser;
        scriptEngineLock = new Object();
        scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        if (scriptEngine == null) {
            throw new IllegalStateException("Could not instantiate JavaScript engine");
        }
        initialiser.initialise(this);
    }

    /**
     * Evaluates a script.
     *
     * The context between evaluations is persisted.
     *
     * @param script the script
     * @param argv the arguments to pass to the script
     * @return the result of the script, which may be <code>null</code>
     * @throws ScriptException if an exception occurs during script evaluation
     */
    public Object evaluateScript(String script, Object... argv) throws ScriptException {
        synchronized (scriptEngineLock) {
            try {
                setVariable("argv", argv);
                return scriptEngine.eval(script);
            } catch (Exception ex) {
                Throwable cause = unwrapNashornExceptions(ex);
                logger.log(Level.WARNING, "Exception while evaluating script", cause);
                throw new ScriptException(
                        String.format("Exception while evaluating script: %1$s", cause.toString()), cause);
            }
        }
    }

    /**
     * Evaluates a script.
     *
     * @param script the script
     * @param argv the arguments to pass to the script
     * @return the result of the script, which may be <code>null</code>
     * @throws ScriptException if an exception occurs during script evaluation
     */
    public Object evaluateScript(Script script, Object... argv) throws ScriptException {
        return evaluateScript(script.getCode(), argv);
    }

    /**
     * Resets the script evaluation context.
     *
     * This removes any variables declared by previously evaluated scripts, and re-initialises the context
     * using the initialiser provided when this ScriptManager was created.
     */
    public void resetEnvironment() {
        synchronized (scriptEngineLock) {
            Bindings newBindings = scriptEngine.createBindings();
            scriptEngine.setBindings(newBindings, ScriptContext.ENGINE_SCOPE);
            initialiser.initialise(this);
        }
    }

    /**
     * Sets a variable in the script evaluation context.
     *
     * @param name The name of the variable to be set
     * @param value The value the variable should be set to
     */
    public void setVariable(String name, Object value) {
        synchronized (scriptEngineLock) {
            scriptEngine.put(name, value);
        }
    }

    /**
     * Fixes silly exception wrapping in Nashorn.
     */
    private static Throwable unwrapNashornExceptions(Throwable t) {
        // Fixes silliness in jdk.nashorn.internal.runtime.ScriptRuntime#apply
        if (t.getCause() != null && t.getClass().equals(RuntimeException.class)) {
            StackTraceElement[] st = t.getStackTrace();
            if (st == null || st.length < 1) {
                return t;
            }

            if (st[0].getClassName().contains("nashorn")) {
                return t.getCause();
            }
        }

        if (t instanceof NullPointerException) {
            StackTraceElement[] st = t.getStackTrace();
            if (st == null || st.length < 1) {
                return t;
            }

            StackTraceElement source = st[0];

            if (source.getClassName().equals("jdk.nashorn.internal.runtime.linker.NashornBeansLinker")
                    && source.getMethodName().equals("getGuardedInvocation")) {
                return new ScriptException("Incorrect number of arguments passed to function call");
            }
        }

        return t;
    }
}
