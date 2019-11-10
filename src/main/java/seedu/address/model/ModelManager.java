package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
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

    private final VersionedTutorAid versionedTutorAid;
    private final UserPrefs userPrefs;
    private Account account;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Earnings> filteredEarnings;
    private final FilteredList<CommandObject> filteredCommands;
    private Stack<String> savedCommand;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Reminder> filteredReminder;
    private final FilteredList<Notes> filteredNotes;
    private HashMap<String, ArrayList<Earnings>> earningsAuto;

    /**
     * Initializes a ModelManager with the given versionedTutorAid and userPrefs.
     */
    public ModelManager(ReadOnlyTutorAid versionedTutorAid, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(versionedTutorAid, userPrefs);

        logger.fine("Initializing with address book: " + versionedTutorAid + " and user prefs " + userPrefs);

        this.versionedTutorAid = new VersionedTutorAid(versionedTutorAid);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.versionedTutorAid.getPersonList());
        filteredEarnings = new FilteredList<>(this.versionedTutorAid.getEarningsList());
        filteredCommands = new FilteredList<>(this.versionedTutorAid.getCommandsList());
        savedCommand = new Stack<String>();
        filteredTasks = new FilteredList<>(this.versionedTutorAid.getTaskList());
        filteredReminder = new FilteredList<>(this.versionedTutorAid.getReminderList());
        filteredNotes = new FilteredList<>(this.versionedTutorAid.getNotesList());
        earningsAuto = new HashMap<String, ArrayList<Earnings>>();
    }

    public ModelManager() {
        this(new TutorAid(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTutorAid versionedTutorAid, ReadOnlyUserPrefs userPrefs, Account acc) {
        this(versionedTutorAid, userPrefs);
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
    public void setTutorAidFilePath(Path versionedTutorAidFilePath) {
        requireNonNull(versionedTutorAidFilePath);
        userPrefs.setTutorAidFilePath(versionedTutorAidFilePath);
    }

    //=========== TutorAid ================================================================================

    @Override
    public void setVersionedTutorAid(ReadOnlyTutorAid versionedTutorAid) {
        this.versionedTutorAid.resetData(versionedTutorAid);
    }

    @Override
    public ReadOnlyTutorAid getTutorAid() {
        return versionedTutorAid;
    }


    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedTutorAid.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedTutorAid.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        versionedTutorAid.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }


    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        versionedTutorAid.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasEarnings(Earnings earnings) {
        requireAllNonNull(earnings);
        return versionedTutorAid.hasEarnings(earnings);
    }

    @Override
    public void addEarnings(Earnings earnings) {
        versionedTutorAid.addEarnings(earnings);
        updateFilteredEarningsList(PREDICATE_SHOW_ALL_EARNINGS);
    }

    @Override
    public void setEarnings(Earnings target, Earnings editedEarnings) {
        requireAllNonNull(target, editedEarnings);
        versionedTutorAid.setEarnings(target, editedEarnings);
    }

    @Override
    public void deleteEarnings(Earnings target) {
        versionedTutorAid.removeEarnings(target);
    }


    @Override
    public boolean hasCommand(CommandObject command) {
        requireAllNonNull(command);
        return versionedTutorAid.hasCommand(command);
    }

    @Override
    public void deleteCommand(CommandObject target) {
        versionedTutorAid.removeCommand(target);
    }

    @Override
    public void addCommand(CommandObject command) {
        versionedTutorAid.addCommand(command);
        updateFilteredCommandsList(PREDICATE_SHOW_ALL_COMMANDS);
    }

    @Override
    public void setCommands(CommandObject target, CommandObject editedCommands) {
        requireAllNonNull(target, editedCommands);
        versionedTutorAid.setCommands(target, editedCommands);
    }

    @Override
    public void saveCommand(String command) {
        this.savedCommand.push(command);
    }

    @Override
    public String getSavedCommand() {
        return this.savedCommand.peek();
    }

    @Override
    public void saveToMap(String key, ArrayList<Earnings> list) {
        this.earningsAuto.put(key, list);
    }

    @Override
    public void saveListToMap(String key, Earnings earnings) {
        this.earningsAuto.get(key).add(earnings);
    }

    @Override
    public HashMap<String, ArrayList<Earnings>> getMap() {
        return this.earningsAuto;
    }

    /**
     *  Checks if the task exists in the TutorAid.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return versionedTutorAid.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        versionedTutorAid.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        versionedTutorAid.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        versionedTutorAid.setTask(target, editedTask);
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
        return (versionedTutorAid.equals(other.versionedTutorAid)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons));
    }

    //=========== Filtered Reminder List Accessors =============================================================

    /**
     *  Checks if the task exists in the TutorAid.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return versionedTutorAid.hasReminder(reminder);
    }

    @Override
    public void addReminder(Reminder reminder) {
        versionedTutorAid.addReminder(reminder);
        updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void deleteReminder(Reminder target) {
        versionedTutorAid.removeReminder(target);
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
        versionedTutorAid.setReminder(reminder, editedReminder);
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
        return versionedTutorAid.hasNotes(note);
    }

    @Override
    public void addNotes(Notes notes) {
        versionedTutorAid.addNotes(notes);
        updateFilteredNotesList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void deleteNotes(Notes target) {
        versionedTutorAid.removeNotes(target);
    }

    @Override
    public void setNotes(Notes target, Notes editedNote) {
        requireAllNonNull(target, editedNote);

        versionedTutorAid.setNotes(target, editedNote);
    }

    @Override
    public void updateFilteredNotesList(Predicate<Notes> predicate) {
        requireNonNull(predicate);
        filteredNotes.setPredicate(predicate);
        //UiManager.startNotes();
    }

    //Undo/Redo
    @Override
    public boolean canUndoTutorAid() {
        return versionedTutorAid.canUndo();
    }

    @Override
    public boolean canRedoTutorAid() {
        return versionedTutorAid.canRedo();
    }

    @Override
    public void undoTutorAid() {
        versionedTutorAid.undo();
    }

    @Override
    public void redoTutorAid() {
        versionedTutorAid.redo();
    }

    @Override
    public void commitTutorAid() {
        versionedTutorAid.commit();
    }
}
