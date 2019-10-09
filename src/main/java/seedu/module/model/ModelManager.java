package seedu.module.model;

import static java.util.Objects.requireNonNull;
import static seedu.module.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.module.commons.core.GuiSettings;
import seedu.module.commons.core.LogsCenter;
import seedu.module.model.module.Module;

/**
 * Represents the in-memory model of the module book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ModuleBook moduleBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Module> filteredModules;

    /**
     * Initializes a ModelManager with the given moduleBook and userPrefs.
     */
    public ModelManager(ReadOnlyModuleBook moduleBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(moduleBook, userPrefs);

        logger.fine("Initializing with module book: " + moduleBook + " and user prefs " + userPrefs);

        this.moduleBook = new ModuleBook(moduleBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredModules = new FilteredList<>(this.moduleBook.getModuleList());
    }

    public ModelManager() {
        this(new ModuleBook(), new UserPrefs());
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
    public Path getModuleBookFilePath() {
        return userPrefs.getModuleBookFilePath();
    }

    @Override
    public void setModuleBookFilePath(Path moduleBookFilePath) {
        requireNonNull(moduleBookFilePath);
        userPrefs.setModuleBookFilePath(moduleBookFilePath);
    }

    //=========== ModuleBook ================================================================================

    @Override
    public void setModuleBook(ReadOnlyModuleBook moduleBook) {
        this.moduleBook.resetData(moduleBook);
    }

    @Override
    public ReadOnlyModuleBook getModuleBook() {
        return moduleBook;
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return moduleBook.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        moduleBook.removeModule(target);
    }

    @Override
    public void addModule(Module module) {
        moduleBook.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    //=========== Filtered module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code module} backed by the internal list of
     * {@code versionedModuleBook}
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
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return moduleBook.equals(other.moduleBook)
                && userPrefs.equals(other.userPrefs)
                && filteredModules.equals(other.filteredModules);
    }

}
