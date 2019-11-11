package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.UniqueTaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class CalendarAddressBook implements ReadOnlyCalendarAddressBook {

    private UniqueTaskList tasks;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
    }

    public CalendarAddressBook() {}

    /**
     * Creates an CalendarAddressBook using the Persons in the {@code toBeCopied}
     */
    public CalendarAddressBook(ReadOnlyCalendarAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the task list with {@code taskList}.
     * {@code taskList} must not contain duplicate taskList.
     */
    public void setTasks(List<Task> taskList) {
        this.tasks.setPersons(taskList);
    }

    public void setTasks(FilteredList<Task> taskList) {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        for (Task t: taskList) {
            uniqueTaskList.add(t);
        }
        this.tasks.setPersons(uniqueTaskList);
    }

    /**
     * Resets the existing data of this {@code CalendarAddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyCalendarAddressBook newData) {
        requireNonNull(newData);

        setTasks(newData.getPersonList());
    }

    //// task-level operations

    /**
     * Returns true if a task with the same identity as {@code task} exists in the address book.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Adds a task to the address book.
     * The task must not already exist in the address book.
     */
    public void addTask(Task p) {
        tasks.add(p);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the address book.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setPerson(target, editedTask);
    }

    /**
     * Removes {@code key} from this {@code CalendarAddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Task> getPersonList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarAddressBook // instanceof handles nulls
                && tasks.equals(((CalendarAddressBook) other).tasks));
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }
}
