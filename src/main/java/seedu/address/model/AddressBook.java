package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.UniqueCommandsList;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.earnings.UniqueEarningsList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UniqueReminderList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;


/**
 * Wraps all data at the address-book level
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueEarningsList earning;
    private final UniqueCommandsList commands;
    private final TaskList tasks;
    private final UniqueReminderList reminder;

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

    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons, Earnings and Commands in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
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
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    /**
     * set new string for task
     * @return a tasks string
     */
    public String toTasksString() {
        return tasks.asUnmodifiableObservableList().size() + " tasks";
        // TODO: refine later
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setEarnings(newData.getEarningsList());
        setCommands(newData.getCommandsList());
        setTasks(newData.getTaskList());

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
     * Removes {@code key} from this {@code AddressBook}.
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
     * Removes {@code key} from this {@code AddressBook}.
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
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEarnings(Earnings key) {
        earning.remove(key);
    }
    //// util methods

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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && (persons.equals(((AddressBook) other).persons))
                || (tasks.equals(((AddressBook) other).tasks)));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
