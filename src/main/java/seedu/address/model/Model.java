package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Object> PREDICATE_SHOW_ALL_ENTRIES = unused -> true;

    /**
     * Replaces internal state with the state in {@code internalState}.
     */
    void setInternalState(InternalState internalState);

    /**
     * Gets the current internal state.
     */
    InternalState getInternalState();

    /**
     * Replaces the current model's context with the given {@code context}.
     */
    void setContext(Context context);

    /**
     * Returns the current model's context.
     */
    Context getContext();

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
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Sets the user prefs' activity book file path.
     */
    void setActivityBookFilePath(Path activityBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Finds Person objects with matching keywords, returning matches in ArrayList.
     */
    ArrayList<Person> findPersonAny(NameContainsKeywordsPredicate predicate);

    /**
     * Finds Person objects with names matching all keywords, returning matches in ArrayList.
     */
    ArrayList<Person> findPersonAll(NameContainsAllKeywordsPredicate predicate);

    /**
     * Finds Person object that has exact matching name as the search term provided, returning an Optional of Person.
     */
    Optional<Person> findPersonByName(String searchTerm);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns the user prefs' activity book file path.
     */
    Path getActivityBookFilePath();

    /**
     * Replaces activity book data with the data in {@code activityBook}.
     */
    void setActivityBook(ActivityBook activityBook);

    /** Returns the ActivityBook */
    ActivityBook getActivityBook();

    /**
     * Deletes the given activity.
     * The activity must exist in the activity book.
     */
    void deleteActivity(Activity target);

    /**
     * Adds the given activity.
     */
    void addActivity(Activity activity);

    /**
     * Replaces the given activity {@code target} with {@code editedActivity}.
     * {@code target} must exist in the activity book.
     */
    void setActivity(Activity target, Activity editedActivity);

    /**
     * Returns an unmodifiable view of the filtered person list for GUI purposes.
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filtered person list to use the {@code Person} filter specified by
     * {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<? super Person> predicate);

    /**
     * Returns an unmodifiable view of the filtered activity list for GUI purposes.
     */
    ObservableList<Activity> getFilteredActivityList();

    /**
     * Updates the filtered activity list to use the {@code Activity} filter specified by
     * {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredActivityList(Predicate<? super Activity> predicate);

    /**
     * Returns an unmodifiable list of {@code Person} containing all participants of a specified
     * {@code Activity}, for GUI purposes.
     */
    List<Person> getAssociatedPersons(Activity activity);

    /**
     * Returns an unmodifiable list of {@code Activity} containing all activities a specified {@code Person}
     * has participated in, for GUI purposes.
     */
    List<Activity> getAssociatedActivities(Person person);
}
