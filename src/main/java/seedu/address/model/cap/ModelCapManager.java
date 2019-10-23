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
import seedu.address.model.cap.person.Semester;
import seedu.address.model.common.Module;

/**
 * Represents the in-memory model of the Cap Log data.
 */
public class ModelCapManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelCapManager.class);

    private final CapLog capLog;
    private final CapUserPrefs capUserPrefs;
    private final FilteredList<Semester> filteredSemesters;
    private final FilteredList<Module> filteredModules;

    /**
     * Initializes a ModelManager with the given CapLog and userPrefs.
     */
    public ModelCapManager(ReadOnlyCapLog capLog, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(capLog, userPrefs);

        logger.fine("Initializing with cap log: " + capLog + " and user prefs " + userPrefs);

        this.capLog = new CapLog(capLog);
        this.capUserPrefs = new CapUserPrefs(userPrefs);
        filteredSemesters = new FilteredList<>(this.capLog.getSemesterList());
        filteredModules = new FilteredList<>(this.capLog.getModuleList());
    }

    public ModelCapManager() {
        this(new CapLog(), new CapUserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setCapUserPrefs(ReadOnlyUserPrefs capUserPrefs) {
        requireNonNull(capUserPrefs);
        this.capUserPrefs.resetData(capUserPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getCapUserPrefs() {
        return capUserPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return capUserPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        capUserPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCapLogFilePath() {
        return capUserPrefs.getCapLogFilePath();
    }

    @Override
    public void setCapLogFilePath(Path capLogFilePath) {
        requireNonNull(capLogFilePath);
        capUserPrefs.setCapLogFilePath(capLogFilePath);
    }

    //=========== CapLog ================================================================================

    @Override
    public void setCapLog(ReadOnlyCapLog capLog) {
        this.capLog.resetData(capLog);
    }

    @Override
    public ReadOnlyCapLog getCapLog() {
        return capLog;
    }

    @Override
    public boolean hasSemester (Semester semester) {
        requireNonNull(semester);
        return capLog.hasSemester(semester);
    }

    @Override
    public void deleteSemester(Semester target) {
        capLog.removeSemester(target);
    }

    @Override
    public void addSemester (Semester semester) {
        capLog.addSemester(semester);
        updateFilteredSemesterList(PREDICATE_SHOW_ALL_SEMESTERS);
    }

    @Override
    public void setSemester(Semester target, Semester editedModule) {
        requireAllNonNull(target, editedModule);
        capLog.setSemester(target, editedModule);
    }

//    @Override
//    public void setSemester(Module target) {
//        requireAllNonNull(target);
//        capLog.setSemester(target);
//    }

    @Override
    public ObservableList<Semester> getFilteredSemesterList() {
        return filteredSemesters;
    }

    @Override
    public void updateFilteredSemesterList(Predicate<Semester> predicate) {
        requireNonNull(predicate);
        filteredSemesters.setPredicate(predicate);
    }
    //=========== Filtered Module List Accessors =============================================================

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
//        capLog.addSemester(module.getSemester());
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
//        updateFilteredSemesterList(PREDICATE_SHOW_ALL_SEMESTERS);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        capLog.setModule(target, editedModule);
    }

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
                && capUserPrefs.equals(other.capUserPrefs)
                && filteredModules.equals(other.filteredModules);
    }

}
