package seedu.address.model.cap;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.common.Module;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
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
     * Returns the user prefs' address book file path.
     */
    Path getCapLogFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setCapLogFilePath(Path capLogFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setCapLog(ReadOnlyModulo capLog);

    /** Returns the AddressBook */
    ReadOnlyModulo getCapLog();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteModule(Module target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addModule(Module module);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setModule(Module target, Module editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);
}
