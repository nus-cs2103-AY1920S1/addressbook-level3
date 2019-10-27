package budgetbuddy.storage.scripts;

import java.io.IOException;
import java.nio.file.Path;

import budgetbuddy.model.ScriptLibrary;

/**
 * Represents storage for {@link budgetbuddy.model.ScriptLibrary}.
 */
public interface ScriptsStorage {
    /**
     * Returns the path scripts are stored at.
     *
     * @return the path scripts are stored at
     */
    Path getScriptsPath();

    /**
     * Reads scripts from the path returned by {@link #getScriptsPath()}.
     *
     * @return the scripts
     * @throws IOException if an IO exception occurs
     */
    default ScriptLibrary readScripts() throws IOException {
        return readScripts(getScriptsPath());
    }

    /**
     * Reads scripts from the given path.
     *
     * @param scriptsPath the path to read scripts from
     * @return the scripts
     * @throws IOException if an IO exception occurs
     */
    ScriptLibrary readScripts(Path scriptsPath) throws IOException;

    /**
     * Saves scripts to the path returned by {@link #getScriptsPath()}.
     *
     * @param scripts the scripts
     * @throws IOException if an IO exception occurs
     */
    default void saveScripts(ScriptLibrary scripts) throws IOException {
        saveScripts(scripts, getScriptsPath());
    }

    /**
     * Saves scripts to the given path.
     *
     * @param scripts the scripts
     * @param scriptsPath the path to save scripts to
     * @throws IOException if an IO exception occurs
     */
    void saveScripts(ScriptLibrary scripts, Path scriptsPath) throws IOException;
}
