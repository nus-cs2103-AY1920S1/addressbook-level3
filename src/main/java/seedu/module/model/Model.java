package seedu.module.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.module.commons.core.GuiSettings;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.Module;
import seedu.module.model.module.TrackedModule;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' module book file path.
     */
    Path getModuleBookFilePath();

    /**
     * Sets the user prefs' module book file path.
     */
    void setModuleBookFilePath(Path moduleBookFilePath);

    /**
     * Replaces module book data with the data in {@code moduleBook}.
     */
    void setModuleBook(ReadOnlyModuleBook moduleBook);

    /**
     * Returns the ModuleBook.
     */
    ReadOnlyModuleBook getModuleBook();

    /**
     * Returns true if a Module with the same identity as {@code Module} exists in the module book.
     */
    boolean hasModule(TrackedModule trackedModule);

    /**
     * Deletes the given module.
     * The module must exist in the module book.
     */
    void deleteModule(TrackedModule target);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the module book.
     */
    void addModule(TrackedModule trackedModule);

    /**
     * Returns an unmodifiable view of the filtered module list.
     */
    ObservableList<TrackedModule> getFilteredModuleList();

    /**
     * Returns an unmodifiable view of the filtered module list within the archive list.
     */
    ObservableList<ArchivedModule> getFilteredArchivedModuleList();

    /**
     * Updates the filter of the filtered archived module list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredArchivedModuleList(Predicate<Module> predicate);

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Returns an unmodifiable view of the current shown list.
     */
    ObservableList<Module> getDisplayedList();

    /**
     * Changes the current list to be shown to the ArchivedModuleList.
     */
    void displayArchivedList();

    /**
     * Changes the current list to be shown to the TrackedModuleList.
     */
    void displayTrackedList();

    /**
     * Returns the active module that is being viewed by the user.
     */
    Optional<Module> getDisplayedModule();

    /**
     * Sets the active module that will be viewed by the user.
     * @param toDisplay the module to be displayed
     */
    void setDisplayedModule(Module toDisplay);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the module book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the module book.
     */
    void setModule(TrackedModule target, TrackedModule editedModule);
}

