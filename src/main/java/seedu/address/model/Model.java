package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.bio.User;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.UniqueFoodList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<User> PREDICATE_SHOW_ALL_USERS = unused -> true;
    //TODO: check what this means
    Predicate<Record> PREDICATE_SHOW_ALL_RECORDS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

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
     * Returns the AddressBook.
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person. The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person. {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}. {@code target} must exist in the address
     * book. The person identity of {@code editedPerson} must not be the same as another existing person in the address
     * book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Replaces food list data with the data in {@code newFoodList}.
     */
    void setFoodList(UniqueFoodList newFoodList);

    boolean hasFood(Food food);

    /**
     * Deletes the given food. The food must exist in the recommendations.
     */
    void deleteFood(Food food);

    /**
     * Adds the given food. {@code food} must not already exist in the recommendations.
     */
    void addFood(Food food);

    /**
     * Returns the a list of foods.
     */
    ObservableList<Food> getFoodList();

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Food> getFilterFoodList();

    /**
     * Updates the filter of the filtered food list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodList(Predicate<Food> predicate);
    void addRecord(Record toAdd);

    boolean hasRecord(Record toAdd);

    void updateFilteredRecordList(Predicate<Record> predicate);

    ReadOnlyRecordBook getRecordBook();

    boolean hasReminder(Reminder reminder);

    void addReminder(Reminder reminder);

    //=========== User List =============================================================

    /**
     * Returns whether or not a user biography already exists.
     * @return
     */
    public boolean bioExists();

    /** Returns the UserList */
    ReadOnlyUserList getUserList();

    /**
     * Returns the user prefs' user list file path.
     */
    Path getUserListFilePath();

    /**
     * Replaces user list data with the data in {@code userList}.
     */
    void setUserList(ReadOnlyUserList userList);

    /**
     * Sets the user prefs' user list file path.
     */
    void setUserListFilePath(Path userListFilePath);

    /**
     * Returns true if a user with the same identity as {@code user} exists in the address book.
     */
    boolean hasUser(User user);

    /**
     * Adds the given user. {@code user} must not already exist in the user list.
     */
    void addUser(User user);

    /**
     * Replaces the given user {@code target} with {@code editedUser}. {@code target} must exist in the address
     * book. The user identity of {@code editedUser} must not be the same as another existing user in the address
     * book.
     */
    void setUser(User target, User editedUser);

    /**
     * Returns an unmodifiable view of the filtered user list
     */
    ObservableList<User> getFilteredUserList();

    /**
     * Updates the filter of the filtered user list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredUserList(Predicate<User> predicate);


}
