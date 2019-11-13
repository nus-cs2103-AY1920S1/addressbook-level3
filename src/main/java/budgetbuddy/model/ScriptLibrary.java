package budgetbuddy.model;

import budgetbuddy.model.script.Script;
import budgetbuddy.model.script.ScriptName;
import javafx.collections.ObservableList;

/**
 * Stores scripts.
 */
public interface ScriptLibrary {
    /**
     * Stores a script. If a script with the same name is already stored, the new script replaces that script.
     *
     * @param s the script to store
     * @return <code>true</code> if a script was replaced; <code>false</code> if there was no script with the
     * same name
     */
    boolean addScript(Script s);

    /**
     * Gets a script by its name.
     *
     * @param name the name of the script to get
     * @return the script, or <code>null</code> if no script by that name exists
     */
    Script getScript(ScriptName name);

    /**
     * Removes a script by its name.
     *
     * @param name the name of the script to remove
     * @return <code>true</code> if a script was removed; <code>false</code> otherwise
     */
    boolean removeScript(ScriptName name);

    /**
     * Returns an unmodifiable view of the stored scripts.
     *
     * @return an unmodifiable view of the stored scripts
     */
    ObservableList<Script> getScriptList();
}
