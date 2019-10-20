package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.bio.User;
import seedu.address.model.bio.UserList;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.UniqueFoodList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<User> filteredUserList;
    private final FilteredList<Person> filteredPersons;
    private final UniqueFoodList foodList;
    private final UserList userList;
    private final FilteredList<Food> filteredFoodList;
    private final UniqueRecordList recordList;
    private final FilteredList<Record> filteredRecordList;
    private final Calendar calendar;
    private final FilteredList<CalendarEntry> filteredCalenderEntryList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlyUserList userList,
                        UniqueFoodList foodList, UniqueRecordList recordList,
                        ReadOnlyCalendar calendar) {
        super();
        requireAllNonNull(addressBook, userPrefs, foodList, userList, recordList, calendar);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs
                + " and food map: " + foodList + " and record list: " + recordList + " and calendar: " + calendar);

        this.addressBook = new AddressBook(addressBook);
        this.userList = new UserList(userList);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredUserList = new FilteredList<>(this.userList.getUserList());
        this.filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.foodList = foodList;
        this.filteredFoodList = new FilteredList<>(this.foodList.asUnmodifiableObservableList());
        this.recordList = recordList;
        this.filteredRecordList = new FilteredList<>(this.recordList.asUnmodifiableObservableList());
        this.calendar = new Calendar(calendar);
        this.filteredCalenderEntryList = new FilteredList<>(this.calendar.getCalendarEntryList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new UserList(), new UniqueFoodList(), new UniqueRecordList(),
                new Calendar());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of {@code
     * versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(
            Object obj) {
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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== User List =============================================================

    @Override
    public void setUserList(ReadOnlyUserList userList) {
        this.userList.resetData(userList);
    }

    @Override
    public boolean bioExists() {
        return !this.userList.isEmpty();
    }

    @Override
    public ReadOnlyUserList getUserList() {
        return userList;
    }

    @Override
    public Path getUserListFilePath() {
        return userPrefs.getUserListFilePath();
    }

    @Override
    public boolean hasUser(User user) {
        requireNonNull(user);
        return userList.hasUser(user);
    }

    @Override
    public void addUser(User user) {
        userList.addUser(user);
        updateFilteredUserList(PREDICATE_SHOW_ALL_USERS);
    }

    /**
     * Returns an unmodifiable view of the list of {@code User} backed by the internal list of {@code
     * versionedAddressBook}
     */
    @Override
    public ObservableList<User> getFilteredUserList() {
        return filteredUserList;
    }

    @Override
    public void updateFilteredUserList(Predicate<User> predicate) {
        requireNonNull(predicate);
        filteredUserList.setPredicate(predicate);
    }

    //Calendar
    @Override
    public ReadOnlyCalendar getCalendar() {
        return calendar;
    }

    @Override
    public boolean hasCalendarEntry(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendar.hasCalendarEntry(calendarEntry);
    }

    @Override
    public void deleteCalendarEntry(CalendarEntry target) {
        calendar.removeCalendarEntry(target);
    }

    @Override
    public void addCalendarEntry(CalendarEntry calendarEntry) {
        calendar.addCalendarEntry(calendarEntry);

    }

    @Override
    public void setCalendarEntry(CalendarEntry target, CalendarEntry editedCalendarEntry) {
        requireAllNonNull(target, editedCalendarEntry);
        calendar.setCalendarEntry(target, editedCalendarEntry);
    }

    @Override
    public ObservableList<CalendarEntry> getFilteredCalendarEntryList() {
        return filteredCalenderEntryList;
    }

    @Override
    public void setUser(User target, User editedUser) {
        requireAllNonNull(target, editedUser);

        userList.setUser(target, editedUser);
    }

    @Override
    public void setUserListFilePath(Path userListFilePath) {
        requireNonNull(userListFilePath);
        userPrefs.setUserListFilePath(userListFilePath);
    }


    //=========== Food Map =============================================================

    //addFood() Function


    @Override
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return foodList.contains(food);
    }

    @Override
    public void deleteFood(Food food) {
        foodList.remove(food);
    }

    @Override
    public void addFood(Food food) {
        foodList.add(food);
    }

    @Override
    public void setFoodList(UniqueFoodList uniqueFoodLists) {
        requireAllNonNull(uniqueFoodLists);
        foodList.setFoods(uniqueFoodLists);
    }

    @Override
    public UniqueFoodList getUniqueFoodListObject() {
        return foodList;
    }

    @Override
    public ObservableList<Food> getFoodList() {
        return foodList.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Food> getFilterFoodList() {
        return filteredFoodList;
    }

    @Override
    public void updateFilteredFoodList(Predicate<Food> predicate) {
        requireNonNull(predicate);
        filteredFoodList.setPredicate(predicate);
    }

    //=========== Records =============================================================
    @Override
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return recordList.contains(record);
    }

    @Override
    public void deleteRecord(Record record) {
        recordList.remove(record);
    }

    @Override
    public void addRecord(Record record) {
        recordList.add(record);
    }

    @Override
    public void setRecordList(UniqueRecordList uniqueRecordLists) {
        requireAllNonNull(uniqueRecordLists);
        recordList.setRecords(uniqueRecordLists);
    }

    @Override
    public UniqueRecordList getUniqueRecordListObject() {
        return recordList;
    }

    @Override
    public ObservableList<Record> getRecordList() {
        return recordList.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Record> getFilterRecordList() {
        return filteredRecordList;
    }

    @Override
    public void updateFilteredRecordList(Predicate<Record> predicate) {
        requireNonNull(predicate);
        filteredRecordList.setPredicate(predicate);
    }
}
