package seedu.address.model.cap;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.common.Module;

/**
 * Represents the in-memory model of the Cap Log data.
 */
public class ModelCapManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelCapManager.class);

    private final CapLog capLog;
    private final UserPrefs userPrefs;
    private final FilteredList<Module> filteredModules;

    /**
     * Initializes a ModelManager with the given CapLog and userPrefs.
     */
    public ModelCapManager(ReadOnlyModulo capLog, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(capLog, userPrefs);

        logger.fine("Initializing with cap log: " + capLog + " and user prefs " + userPrefs);

        this.capLog = new CapLog(capLog);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredModules = new FilteredList<Module>(this.capLog.getModuleList());
    }

    public ModelCapManager() {
        this(new CapLog(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCapLogFilePath() {
        return userPrefs.getCapLogFilePath();
    }

    @Override
    public void setCapLogFilePath(Path capLogFilePath) {
        requireNonNull(capLogFilePath);
        userPrefs.setCapLogFilePath(capLogFilePath);
    }

    //=========== CapLog ================================================================================

    @Override
    public void setCapLog(ReadOnlyModulo capLog) {
        this.capLog.resetData(capLog);
    }

    @Override
    public ReadOnlyModulo getCapLog() {
        return capLog;
    }

    @Override
    public boolean hasModule (Module module) {
        requireNonNull(module);
        return capLog.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        capLog.removeModule(target);
    }

    @Override
    public void addModule (Module module) {
        capLog.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        capLog.setModule(target, editedModule);
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedCapLog}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelCapManager)) {
            return false;
        }

        // state check
        ModelCapManager other = (ModelCapManager) obj;
        return capLog.equals(other.capLog)
                && userPrefs.equals(other.userPrefs)
                && filteredModules.equals(other.filteredModules);
    }

}
