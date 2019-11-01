package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.account.Account;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.note.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.task.Task;
import seedu.address.ui.UiManager;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static boolean loggedIn = false;

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private Account account;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Earnings> filteredEarnings;
    private final FilteredList<CommandObject> filteredCommands;
    private Stack<String> savedCommand;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Reminder> filteredReminder;
    private final FilteredList<Notes> filteredNotes;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredEarnings = new FilteredList<>(this.addressBook.getEarningsList());
        filteredCommands = new FilteredList<>(this.addressBook.getCommandsList());
        savedCommand = new Stack<String>();
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
        filteredReminder = new FilteredList<>(this.addressBook.getReminderList());
        filteredNotes = new FilteredList<>(this.addressBook.getNotesList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    /*public ModelManager(Account acc) {
        this(new AddressBook(), new UserPrefs());
        this.account = acc;
    }*/

    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, Account acc) {
        this(addressBook, userPrefs);
        this.account = acc;
        loggedIn = true;
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
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }


    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
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

    @Override
    public boolean hasEarnings(Earnings earnings) {
        requireAllNonNull(earnings);
        return addressBook.hasEarnings(earnings);
    }

    @Override
    public void addEarnings(Earnings earnings) {
        addressBook.addEarnings(earnings);
        updateFilteredEarningsList(PREDICATE_SHOW_ALL_EARNINGS);
    }

    @Override
    public void setEarnings(Earnings target, Earnings editedEarnings) {
        requireAllNonNull(target, editedEarnings);

        addressBook.setEarnings(target, editedEarnings);
    }

    @Override
    public void deleteEarnings(Earnings target) {
        addressBook.removeEarnings(target);
    }


    @Override
    public boolean hasCommand(CommandObject command) {
        requireAllNonNull(command);
        return addressBook.hasCommand(command);
    }

    @Override
    public void deleteCommand(CommandObject target) {
        addressBook.removeCommand(target);
    }

    @Override
    public void addCommand(CommandObject command) {
        addressBook.addCommand(command);
        updateFilteredCommandsList(PREDICATE_SHOW_ALL_COMMANDS);
    }

    @Override
    public void setCommands(CommandObject target, CommandObject editedCommands) {
        requireAllNonNull(target, editedCommands);

        addressBook.setCommands(target, editedCommands);
    }

    @Override
    public void saveCommand(String command) {
        this.savedCommand.push(command);
    }

    @Override
    public String getSavedCommand() {
        return this.savedCommand.peek();
    }

    /**
     *  Checks if the task exists in the addressbook.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return addressBook.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        addressBook.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        addressBook.addTask(task);
        //updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void afterAddTask() {
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        addressBook.setTask(target, editedTask);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        UiManager.startStudentProfile();
    }

    public ObservableList<Earnings> getFilteredEarningsList() {
        return filteredEarnings;
    }


    public ObservableList<CommandObject> getFilteredCommandsList() {
        return filteredCommands;
    }

    public ObservableList<Reminder> getFilteredReminderList() {
        return filteredReminder;
    }

    public ObservableList<Notes> getFilteredNotesList() {
        return filteredNotes;
    }

    @Override
    public void updateFilteredEarningsList(Predicate<Earnings> predicate) {
        requireNonNull(predicate);
        filteredEarnings.setPredicate(predicate);
        UiManager.startEarnings();
    }

    @Override
    public void updateFilteredCalendarList() {
        UiManager.startCalendar();
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
        UiManager.startTasks();
    }

    @Override
    public void updateFilteredCommandsList(Predicate<CommandObject> predicate) {
        requireNonNull(predicate);
        filteredCommands.setPredicate(predicate);
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
        return (addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons));
    }

    //=========== Filtered Reminder List Accessors =============================================================

    /**
     *  Checks if the task exists in the addressbook.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return addressBook.hasReminder(reminder);
    }

    @Override
    public void addReminder(Reminder reminder) {
        addressBook.addReminder(reminder);
        updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteReminder(Reminder target) {
        addressBook.removeReminder(target);
    }

    @Override
    public void updateFilteredReminderList(Predicate<Reminder> predicate) {
        requireNonNull(predicate);
        filteredReminder.setPredicate(predicate);
        UiManager.startReminders();
    }

    @Override
    public void setReminder(Reminder reminder, Reminder editedReminder) {
        requireAllNonNull(reminder, editedReminder);
        addressBook.setReminder(reminder, editedReminder);
    }

    public Account getAccount() {
        return account;
    }

    public void isLoggedIn() {
        loggedIn = true;
    }

    public void isLoggedOut() {
        loggedIn = false;
    }

    @Override
    public boolean userHasLoggedIn() {
        return loggedIn;
    }

    @Override
    public boolean hasNotes(Notes note) {
        requireNonNull(note);
        return addressBook.hasNotes(note);
    }

    @Override
    public void addNotes(Notes notes) {
        addressBook.addNotes(notes);
        updateFilteredNotesList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void deleteNotes(Notes target) {
        addressBook.removeNotes(target);
    }

    @Override
    public void setNotes(Notes target, Notes editedNote) {
        requireAllNonNull(target, editedNote);

        addressBook.setNotes(target, editedNote);
    }

    @Override
    public void updateFilteredNotesList(Predicate<Notes> predicate) {
        requireNonNull(predicate);
        filteredNotes.setPredicate(predicate);
        UiManager.startNotes();
    }
}
