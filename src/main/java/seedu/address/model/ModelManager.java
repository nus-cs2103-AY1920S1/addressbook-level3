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

    private final TutorAid tutorAid;
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
     * Initializes a ModelManager with the given tutorAid and userPrefs.
     */
    public ModelManager(ReadOnlyTutorAid tutorAid, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(tutorAid, userPrefs);

        logger.fine("Initializing with address book: " + tutorAid + " and user prefs " + userPrefs);

        this.tutorAid = new TutorAid(tutorAid);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.tutorAid.getPersonList());
        filteredEarnings = new FilteredList<>(this.tutorAid.getEarningsList());
        filteredCommands = new FilteredList<>(this.tutorAid.getCommandsList());
        savedCommand = new Stack<String>();
        filteredTasks = new FilteredList<>(this.tutorAid.getTaskList());
        filteredReminder = new FilteredList<>(this.tutorAid.getReminderList());
        filteredNotes = new FilteredList<>(this.tutorAid.getNotesList());
    }

    public ModelManager() {
        this(new TutorAid(), new UserPrefs());
    }

    /*public ModelManager(Account acc) {
        this(new TutorAid(), new UserPrefs());
        this.account = acc;
    }*/

    public ModelManager(ReadOnlyTutorAid tutorAid, ReadOnlyUserPrefs userPrefs, Account acc) {
        this(tutorAid, userPrefs);
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
    public Path getTutorAidFilePath() {
        return userPrefs.getTutorAidFilePath();
    }

    @Override
    public void setTutorAidFilePath(Path tutorAidFilePath) {
        requireNonNull(tutorAidFilePath);
        userPrefs.setTutorAidFilePath(tutorAidFilePath);
    }

    //=========== TutorAid ================================================================================

    @Override
    public void setTutorAid(ReadOnlyTutorAid tutorAid) {
        this.tutorAid.resetData(tutorAid);
    }

    @Override
    public ReadOnlyTutorAid getTutorAid() {
        return tutorAid;
    }


    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return tutorAid.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        tutorAid.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        tutorAid.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }


    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        tutorAid.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasEarnings(Earnings earnings) {
        requireAllNonNull(earnings);
        return tutorAid.hasEarnings(earnings);
    }

    @Override
    public void addEarnings(Earnings earnings) {
        tutorAid.addEarnings(earnings);
        updateFilteredEarningsList(PREDICATE_SHOW_ALL_EARNINGS);
    }

    @Override
    public void setEarnings(Earnings target, Earnings editedEarnings) {
        requireAllNonNull(target, editedEarnings);

        tutorAid.setEarnings(target, editedEarnings);
    }

    @Override
    public void deleteEarnings(Earnings target) {
        tutorAid.removeEarnings(target);
    }


    @Override
    public boolean hasCommand(CommandObject command) {
        requireAllNonNull(command);
        return tutorAid.hasCommand(command);
    }

    @Override
    public void deleteCommand(CommandObject target) {
        tutorAid.removeCommand(target);
    }

    @Override
    public void addCommand(CommandObject command) {
        tutorAid.addCommand(command);
        updateFilteredCommandsList(PREDICATE_SHOW_ALL_COMMANDS);
    }

    @Override
    public void setCommands(CommandObject target, CommandObject editedCommands) {
        requireAllNonNull(target, editedCommands);

        tutorAid.setCommands(target, editedCommands);
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
     *  Checks if the task exists in the TutorAid.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tutorAid.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        tutorAid.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        tutorAid.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        tutorAid.setTask(target, editedTask);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedTutorAid}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        //UiManager.startStudentProfile();
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
        //UiManager.startEarnings();
    }

    @Override
    public void updateFilteredCalendarList() {
        UiManager.startCalendar();
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedTutorAid}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
        //UiManager.startTasks();
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
        return (tutorAid.equals(other.tutorAid)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons));
    }

    //=========== Filtered Reminder List Accessors =============================================================

    /**
     *  Checks if the task exists in the TutorAid.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return tutorAid.hasReminder(reminder);
    }

    @Override
    public void addReminder(Reminder reminder) {
        tutorAid.addReminder(reminder);
        updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteReminder(Reminder target) {
        tutorAid.removeReminder(target);
    }

    @Override
    public void updateFilteredReminderList(Predicate<Reminder> predicate) {
        requireNonNull(predicate);
        filteredReminder.setPredicate(predicate);
        //UiManager.startReminders();
    }

    @Override
    public void setReminder(Reminder reminder, Reminder editedReminder) {
        requireAllNonNull(reminder, editedReminder);
        tutorAid.setReminder(reminder, editedReminder);
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
        return tutorAid.hasNotes(note);
    }

    @Override
    public void addNotes(Notes notes) {
        tutorAid.addNotes(notes);
        updateFilteredNotesList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void deleteNotes(Notes target) {
        tutorAid.removeNotes(target);
    }

    @Override
    public void setNotes(Notes target, Notes editedNote) {
        requireAllNonNull(target, editedNote);

        tutorAid.setNotes(target, editedNote);
    }

    @Override
    public void updateFilteredNotesList(Predicate<Notes> predicate) {
        requireNonNull(predicate);
        filteredNotes.setPredicate(predicate);
        //UiManager.startNotes();
    }
}
