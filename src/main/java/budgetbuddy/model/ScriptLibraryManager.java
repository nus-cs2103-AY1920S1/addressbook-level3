package budgetbuddy.model;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import budgetbuddy.model.script.Script;
import budgetbuddy.model.script.ScriptName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the script library loaded in the application.
 */
public class ScriptLibraryManager implements ScriptLibrary {
    private final Object listLock = new Object();
    private final ObservableList<Script> scriptList = FXCollections.observableArrayList();
    private final ObservableList<Script> unmodifiableScriptList = FXCollections.unmodifiableObservableList(scriptList);
    private final HashMap<ScriptName, Integer> nameToIndexCache = new HashMap<>();

    /**
     * Constructs a script manager with no scripts.
     */
    public ScriptLibraryManager() {
    }

    /**
     * Constructs a script manager and populates it with the given scripts.
     *
     * @param scripts the scripts to populate the manager with
     */
    public ScriptLibraryManager(Collection<Script> scripts) {
        requireAllNonNull(scripts);
        synchronized (listLock) {
            scriptList.setAll(scripts);
            reconstructCache();
        }
    }

    @Override
    public boolean addScript(Script s) {
        requireNonNull(s);
        synchronized (listLock) {
            Integer oldIndex = nameToIndexCache.get(s.getName());
            if (oldIndex != null) {
                scriptList.set(oldIndex, s);
                return true;
            } else {
                scriptList.add(s);
                nameToIndexCache.put(s.getName(), scriptList.size() - 1);
                return false;
            }
        }
    }

    @Override
    public Script getScript(ScriptName name) {
        synchronized (listLock) {
            Integer index = nameToIndexCache.get(name);
            if (index == null) {
                return null;
            }
            return scriptList.get(index);
        }
    }

    @Override
    public boolean removeScript(ScriptName name) {
        synchronized (listLock) {
            Integer index = nameToIndexCache.get(name);
            if (index == null) {
                return false;
            }
            scriptList.remove(index.intValue());
            // The thinking is that removing a script doesn't happen too often.
            reconstructCache();
            return true;
        }
    }

    /**
     * Reconstructs the name to index cache.
     */
    private void reconstructCache() {
        synchronized (listLock) {
            nameToIndexCache.clear();
            // Java's Streams library just stops short of being useful
            // there is no way to enumerate a stream, nor zip
            // back to the 90s we go:
            for (int i = 0; i < scriptList.size(); ++i) {
                Script script = scriptList.get(i);
                nameToIndexCache.put(script.getName(), i);
            }
        }
    }

    @Override
    public ObservableList<Script> getScriptList() {
        return unmodifiableScriptList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScriptLibraryManager that = (ScriptLibraryManager) o;
        return scriptList.equals(that.scriptList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scriptList);
    }
}
