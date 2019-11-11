package seedu.module.model;

import static java.util.Objects.requireNonNull;
import static seedu.module.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.module.commons.core.GuiSettings;
import seedu.module.commons.core.LogsCenter;
import seedu.module.commons.core.Messages;
import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.Module;
import seedu.module.model.module.TrackedModule;
import seedu.module.model.module.predicate.SameModuleCodePredicate;

/**
 * Represents the in-memory model of the module book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ModuleBook moduleBook;
    private final UserPrefs userPrefs;
    private FilteredList<TrackedModule> filteredTrackedModules;
    private FilteredList<ArchivedModule> filteredArchivedModules;
    private DisplayedModuleList displayedList = new DisplayedModuleList();
    private Optional<Module> displayedModule = Optional.empty();

    /**
     * Initializes a ModelManager with the given moduleBook and userPrefs.
     */
    public ModelManager(ReadOnlyModuleBook moduleBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(moduleBook, userPrefs);

        logger.fine("Initializing with module book: " + moduleBook + " and user prefs " + userPrefs);

        this.moduleBook = new ModuleBook(moduleBook);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredArchivedModules = new FilteredList<>(this.moduleBook.getArchivedModuleList());
        filteredTrackedModules = new FilteredList<>(this.moduleBook.getModuleList());
        showAllTrackedModules();
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
        filteredTrackedModules = new FilteredList<>(this.moduleBook.getModuleList());
        filteredArchivedModules = new FilteredList<>(this.moduleBook.getArchivedModuleList());
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the module book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the module book.
     */
    public void setModule(TrackedModule target, TrackedModule editedModule) {
        requireNonNull(editedModule);
        moduleBook.setModule(target, editedModule);
    }

    @Override
    public ReadOnlyModuleBook getModuleBook() {
        return moduleBook;
    }

    @Override
    public boolean hasModule(TrackedModule trackedModule) {
        requireNonNull(trackedModule);
        return moduleBook.hasModule(trackedModule);
    }

    @Override
    public void deleteModule(TrackedModule target) {
        moduleBook.removeModule(target);
    }

    @Override
    public void addModule(TrackedModule trackedModule) {
        moduleBook.addModule(trackedModule);
    }

    //=========== Filtered module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code module} backed by the internal list of.
     * {@code versionedModuleBook}
     */
    @Override
    public ObservableList<TrackedModule> getFilteredModuleList() {
        return filteredTrackedModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredTrackedModules.setPredicate(predicate);
    }

    /**
     * Finds and returns an {@literal Optional<TrackedModule>} from filteredArchivedModules based on the predicate.
     * Returns an empty Optional if the module is not found.
     */
    @Override
    public Optional<TrackedModule> findTrackedModule(SameModuleCodePredicate predicate) {
        Optional<TrackedModule> foundModule = moduleBook.getModuleList().stream()
                .filter(predicate)
                .findFirst();
        return foundModule;
    }

    public TrackedModule getTrackedModuleByIndex(Model model, Index index) throws CommandException {
        List<TrackedModule> lastShownList = model.getFilteredModuleList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }
        TrackedModule moduleToReturn = lastShownList.get(index.getZeroBased());
        return moduleToReturn;
    }

    //=========== Filtered ArchivedModule List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ArchivedModule} backed by the internal list of.
     * {@code versionedModuleBook}
     */
    @Override
    public ObservableList<ArchivedModule> getFilteredArchivedModuleList() {
        return filteredArchivedModules;
    }

    @Override
    public void updateFilteredArchivedModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredArchivedModules.setPredicate(predicate);
    }

    /**
     * Finds and returns an {@literal Optional<ArchivedModule>} from filteredArchivedModules based on the predicate.
     * Returns an empty Optional if the module is not found.
     */
    @Override
    public Optional<ArchivedModule> findArchivedModule(SameModuleCodePredicate predicate) {
        Optional<ArchivedModule> foundModule = moduleBook.getArchivedModuleList().stream()
                .filter(predicate)
                .findFirst();
        return foundModule;
    }

    //=========== Displayed List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} based on command.
     */
    @Override
    public ObservableList<Module> getDisplayedList() {
        return displayedList.getObservableList();
    }

    @Override
    public void updateDisplayedList() {
        this.displayedList.clear();
        for (Module m : filteredArchivedModules) {
            displayedList.add(m);
        }

        for (Module m : filteredTrackedModules) {
            displayedList.add(m);
        }
    }

    @Override
    public void showAllTrackedModules() {
        updateFilteredArchivedModuleList(PREDICATE_SHOW_NO_MODULES);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        updateDisplayedList();
    }

    //=========== Displayed Module Accessors =============================================================

    @Override
    public Optional<Module> getDisplayedModule() {
        return displayedModule;
    }

    @Override
    public void setDisplayedModule(Module toDisplay) {
        this.displayedModule = Optional.ofNullable(toDisplay);
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
                // TODO: Figure out how to test the filtered lists
                // && filteredTrackedModules.equals(other.filteredTrackedModules)
                // && filteredArchivedModules.equals(other.filteredArchivedModules)
                && displayedModule.equals(other.displayedModule);
    }

}
