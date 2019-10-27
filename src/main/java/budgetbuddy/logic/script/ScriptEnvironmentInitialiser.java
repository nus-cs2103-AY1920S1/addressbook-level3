package budgetbuddy.logic.script;

/**
 * Represents a function that initialises a {@link ScriptEngine}.
 */
@FunctionalInterface
public interface ScriptEnvironmentInitialiser {
    /**
     * Initialises the {@link ScriptEngine}.
     *
     * @param engine the {@link ScriptEngine} to initialise
     */
    void initialise(ScriptEngine engine);
}
