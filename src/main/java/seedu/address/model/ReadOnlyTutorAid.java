package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.note.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.task.Task;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTutorAid {

    /**
     * Returns an unmodifiable view of the persons, earnings and commands list.
     * This list will not contain any duplicate persons, earnings or commands.
     */
    ObservableList<Person> getPersonList();
    ObservableList<Earnings> getEarningsList();
    ObservableList<CommandObject> getCommandsList();

    /**
     * Returns an unmodifiable view of the task list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();
    ObservableList<Reminder> getReminderList();
    ObservableList<Notes> getNotesList();
}
