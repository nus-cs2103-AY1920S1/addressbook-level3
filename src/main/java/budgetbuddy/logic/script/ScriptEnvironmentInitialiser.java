package budgetbuddy.logic.script;

/**
 * Represents a function that initialises a {@link ScriptManager}.
 */
@FunctionalInterface
public interface ScriptEnvironmentInitialiser {
    /**
     * Initialises the {@link ScriptManager}.
     *
     * @param engine the {@link ScriptManager} to initialise
     */
    void initialise(ScriptManager engine);
}
