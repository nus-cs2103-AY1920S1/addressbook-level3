package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.UniqueCommandsList;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.earnings.UniqueEarningsList;
import seedu.address.model.note.Notes;
import seedu.address.model.note.UniqueNotesList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UniqueReminderList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;


/**
 * Wraps all data at the address-book level
 */
public class TutorAid implements ReadOnlyTutorAid {

    private final UniquePersonList persons;
    private final UniqueEarningsList earning;
    private final UniqueCommandsList commands;
    private final TaskList tasks;
    private final UniqueReminderList reminder;
    private final UniqueNotesList notes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        earning = new UniqueEarningsList();
        commands = new UniqueCommandsList();
        tasks = new TaskList();
        reminder = new UniqueReminderList();
        notes = new UniqueNotesList();

    }

    public TutorAid() {}

    /**
     * Creates an TutorAid using the Persons, Earnings and Commands in the {@code toBeCopied}
     */
    public TutorAid(ReadOnlyTutorAid toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the earnings list with {@code earnings}.
     * {@code earnings} must not contain duplicate earnings.
     */
    public void setEarnings(List<Earnings> earnings) {
        this.earning.setEarnings(earnings);
    }

    /**
     * Replaces the given earnings {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setEarnings(Earnings target, Earnings editedEarnings) {
        requireNonNull(editedEarnings);

        earning.setEarnings(target, editedEarnings);
    }

    //// task-level operations
    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    /**
     * Removes {@code key} from this {@code TutorAid}.
     * {@code key} must exist in the address book.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    //// person-level operations

    /**
     * Replaces the contents of the commands list with {@code commands}.
     * {@code commands} must not contain duplicate commands.
     */
    public void setCommands(List<CommandObject> commands) {
        this.commands.setCommands(commands);
    }

    /**
     * Replaces the given commands {@code target} in the list with {@code editedCommands}.
     * {@code target} must exist in the address book.
     * The command identity of {@code editedCommands} must not be the same as
     * another existing command in the address book.
     */
    public void setCommands(CommandObject target, CommandObject editedCommands) {
        requireNonNull(editedCommands);

        commands.setCommands(target, editedCommands);
    }

    /**
     * Resets the existing data of this {@code TutorAid} with {@code newData}.
     */
    public void resetData(ReadOnlyTutorAid newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setEarnings(newData.getEarningsList());
        setCommands(newData.getCommandsList());
        setTasks(newData.getTaskList());
        setReminder(newData.getReminderList());
        setNotes(newData.getNotesList());

    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code TutorAid}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Returns true if a command with the same identity as {@code command} exists in the address book.
     */
    public boolean hasCommand(CommandObject command) {
        requireNonNull(command);
        return commands.contains(command);
    }

    /**
     * Adds a command to the address book.
     * The command must not already exist in the address book.
     */
    public void addCommand(CommandObject e) {
        commands.add(e);
    }

    /**
     * Removes {@code key} from this {@code TutorAid}.
     * {@code key} must exist in the address book.
     */
    public void removeCommand(CommandObject key) {
        commands.remove(key);
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasEarnings(Earnings earnings) {
        requireNonNull(earnings);
        return earning.contains(earnings);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addEarnings(Earnings e) {
        earning.add(e);
    }

    /**
     * Removes {@code key} from this {@code TutorAid}.
     * {@code key} must exist in the address book.
     */
    public void removeEarnings(Earnings key) {
        earning.remove(key);
    }
    //// util methods

    /**
     * Adds a reminder to the address book.
     * The person must not already exist in the address book.
     */
    public void addReminder(Reminder r) {
        reminder.add(r);
    }

    /**
     * Removes {@code key} from this {@code TutorAid}.
     * {@code key} must exist in the address book.
     */
    public void removeReminder(Reminder key) {
        reminder.remove(key);
    }

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in the address book.
     */
    public boolean hasReminder(Reminder reminders) {
        requireNonNull(reminders);
        return reminder.contains(reminders);
    }

    /**
     * Replaces the given reminder {@code target} in the list with {@code editedReminder}.
     * {@code target} must exist in the address book.
     * The reminder identity of {@code editedReminder} must not be the same as another reminder in the address book.
     */
    public void setReminder(Reminder reminders, Reminder editedReminder) {
        requireNonNull(editedReminder);
        reminder.setReminder(reminders, editedReminder);
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setReminder(List<Reminder> reminders) {
        this.reminder.setReminder(reminders);
    }

    /**
     * Add note into address book.
     * @param e notes.
     */
    public void addNotes(Notes e) {
        notes.add(e);
    }

    /**
     * Returns true if a code with the same identity as {@code note} exists in the address book.
     * @param note Note.
     * @return true of false if the notes is available.
     */
    public boolean hasNotes(Notes note) {
        requireNonNull(note);
        return notes.contains(note);
    }

    /**
     * Removes {@code key} from this {@code TutorAid}.
     * {@code key} must exist in the address book.
     */
    public void removeNotes(Notes key) {
        notes.remove(key);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedNote}.
     * {@code target} must exist in the address book.
     * The note identity of {@code editedNote} must not be the same as another existing note in the address book.
     */
    public void setNotes(Notes target, Notes editedNote) {
        requireNonNull(editedNote);
        notes.setNotes(target, editedNote);
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setNotes(List<Notes> note) {
        this.notes.setNotes(note);
    }

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Earnings> getEarningsList() {
        return earning.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<CommandObject> getCommandsList() {
        return commands.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Reminder> getReminderList() {
        return reminder.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Notes> getNotesList() {
        return notes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TutorAid // instanceof handles nulls
                && (persons.equals(((TutorAid) other).persons))
                && (tasks.equals(((TutorAid) other).tasks)));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
