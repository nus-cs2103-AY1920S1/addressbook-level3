package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;
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

    public boolean hasReminder(Reminder reminder);

    public void addReminder(Reminder reminder);


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
     * Returns the {@code UniqueFoodList} object.
     */
    UniqueFoodList getUniqueFoodListObject();

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


    //==================RECORD====================
    /**
     * Replaces food list data with the data in {@code newFoodList}.
     */
    void setRecordList(UniqueRecordList newRecordList);

    boolean hasRecord(Record record);

    /**
     * Deletes the given food. The food must exist in the recommendations.
     */
    void deleteRecord(Record record);

    /**
     * Adds the given food. {@code food} must not already exist in the recommendations.
     */
    void addRecord(Record record);

    /**
     * Returns the {@code UniqueFoodList} object.
     */
    UniqueRecordList getUniqueRecordListObject();

    /**
     * Returns the a list of foods.
     */
    ObservableList<Record> getRecordList();

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Record> getFilterRecordList();

    /**
     * Updates the filter of the filtered food list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecordList(Predicate<Record> predicate);

}
