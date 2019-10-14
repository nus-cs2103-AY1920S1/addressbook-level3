package seedu.address.profile;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.profile.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns the user prefs' DukeCooks file path.
     */
    Path getDukeCooksFilePath();

    /**
     * Sets the user prefs' Duke Cooks file path.
     */
    void setDukeCooksFilePath(Path dukeCooksFilePath);

    /**
     * Replaces Duke Cooks data with the data in {@code dukeCooks}.
     */
    void setDukeCooks(ReadOnlyDukeCooks dukeCooks);

    /** Returns DukeCooks */
    ReadOnlyDukeCooks getDukeCooks();

    /**
     * Adds the given person.
     * {@code person} must not already exist in Duke Cooks.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in Duke Cooks.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the Duke Cooks.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
}
