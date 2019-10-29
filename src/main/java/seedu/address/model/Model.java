package seedu.address.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.aesthetics.Background;
import seedu.address.model.aesthetics.Colour;
import seedu.address.model.bio.User;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordType;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.model.statistics.AverageType;
import sugarmummy.recmfood.model.Food;
import sugarmummy.recmfood.model.UniqueFoodList;

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


    //==================Food List====================

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    boolean hasFood(Food food);

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
     * Replaces food list data with the data in {@code newFoodList}.
     */
    void setFoodList(UniqueFoodList newFoodList);

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

    boolean hasRecord(Record record);

    /**
     * Deletes the given record. The record must exist in the recommendations.
     */
    void deleteRecord(Record record);

    /**
     * Adds the given record. {@code record} must not already exist in the record list.
     */
    void addRecord(Record record);

    /**
     * Returns the {@code UniqueRecordList} object.
     */
    UniqueRecordList getUniqueRecordListObject();

    /**
     * Returns the a list of records.
     */
    ObservableList<Record> getRecordList();

    /**
     * Replaces record list data with the data in {@code newRecordList}.
     */
    void setRecordList(UniqueRecordList newRecordList);

    /**
     * Returns an unmodifiable view of the filtered record list
     */
    ObservableList<Record> getFilterRecordList();

    /**
     * Updates the filter of the filtered record list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecordList(Predicate<Record> predicate);

    //=========== Statistics List =============================================================

    /**
     * Returns the last average type calculated.
     */
    SimpleStringProperty getAverageType();

    /**
     * Returns the last record type whose average is calculated.
     */
    SimpleStringProperty getRecordType();

    /**
     * Calculates average values of a record type.
     */
    void calculateAverageMap(AverageType averageType, RecordType recordType, int count);

    /**
     * Returns a {@code AverageMap} object that maps time period to the respective average values.
     */
    ObservableMap<LocalDate, Double> getAverageMap();

    //=========== User List =============================================================

    /**
     * Returns whether or not a user biography already exists.
     *
     * @return
     */
    boolean bioExists();

    /**
     * Returns the UserList
     */
    ReadOnlyUserList getUserList();

    /**
     * Replaces user list data with the data in {@code userList}.
     */
    void setUserList(ReadOnlyUserList userList);

    /**
     * Returns the user prefs' user list file path.
     */
    Path getUserListFilePath();

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
     * Replaces the given user {@code target} with {@code editedUser}. {@code target} must exist in the address book.
     * The user identity of {@code editedUser} must not be the same as another existing user in the address book.
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

    /**
     * Returns the Calendar.
     */
    ReadOnlyCalendar getCalendar();

    /**
     * Returns true if a calendar entry with the same identity as {@code calendarEntry} exists in the calendar.
     */
    boolean hasCalendarEntry(CalendarEntry calendarEntry);

    /**
     * Deletes the given calendarEntry. The calendarEntry must exist in the calendar.
     */
    void deleteCalendarEntry(CalendarEntry target);

    /**
     * Adds the given calendar entry. {@code calendarEntry} must not already exist in the calendar.
     */
    void addCalendarEntry(CalendarEntry calendarEntry);

    /**
     * Adds a list reminders to the calendar. Only {@code Reminders} that don't already exist in the list will be
     * added.
     */
    void addPastReminders(List<Reminder> reminders);

    /**
     * Replaces the given calendarEntry {@code target} with {@code editedCalendarEntry}. {@code target} must exist in
     * the calendar. The calendarEntry identity of {@code editedCalendarEntry} must not be the same as another existing
     * calendar entry in the calendar.
     */
    void setCalendarEntry(CalendarEntry target, CalendarEntry editedCalendarEntry);

    /**
     * Returns an unmodifiable view of the filtered calendar entry list
     */
    ObservableList<CalendarEntry> getFilteredCalendarEntryList();

    /**
     * Returns an unmodifiable view of the past reminder list
     */
    ObservableList<CalendarEntry> getPastReminderList();

    /**
     * Reschedule upcoming reminders.
     */
    void schedule();

    /**
     * Stop all upcoming reminders.
     */
    void stopAllReminders();

    //=========== Aesthetics =============================================================

    /**
     * Returns the font colour to be set for this app.
     */
    Colour getFontColour();

    /**
     * Sets the font colour of this application and saves it to the user preferences file.
     */
    void setFontColour(Colour fontColour);

    /**
     * Returns the background to be set for this app.
     */
    Background getBackground();

    /**
     * Sets the background of this application and saves it to the user preferences file.
     */
    void setBackground(Background background);

    //=========== Motivational Quotes =============================================================

    /**
     * Returns an unmodifiable list of motivational quotes stored in this program.
     */
    public List<String> getMotivationalQuotesList();

}
