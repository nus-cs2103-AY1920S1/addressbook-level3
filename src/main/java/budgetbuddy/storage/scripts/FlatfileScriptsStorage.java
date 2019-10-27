package budgetbuddy.storage.scripts;

import java.io.IOException;
import java.nio.file.Path;

import budgetbuddy.model.ScriptLibrary;
import budgetbuddy.model.ScriptLibraryManager;

/**
 * Stores scripts as separate files in a directory.
 */
public class FlatfileScriptsStorage implements ScriptsStorage {
    private Path scriptsPath;

    /**
     * Constructs a flatfile script storage that stores scripts at the specified path.
     * @param scriptsPath the path at which to store scripts
     */
    public FlatfileScriptsStorage(Path scriptsPath) {
        this.scriptsPath = scriptsPath;
    }

    @Override
    public Path getScriptsPath() {
        return scriptsPath;
    }

    @Override
    public ScriptLibrary readScripts(Path scriptsPath) throws IOException {
        // TODO
        return new ScriptLibraryManager();
    }

    @Override
    public void saveScripts(ScriptLibrary scripts, Path scriptsPath) throws IOException {
        // TODO
    }
}
