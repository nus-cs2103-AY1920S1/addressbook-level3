package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.person.Person;
<<<<<<< HEAD
import seedu.address.model.task.Task;
=======
import seedu.address.model.reminder.Reminder;
>>>>>>> 0987fd265f1ca3b1710eab3356b13daf274876a7

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();
    ObservableList<Earnings> getEarningsList();
<<<<<<< HEAD

    /**
     * Returns an unmodifiable view of the task list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();
=======
    ObservableList<Reminder> getReminderList();
>>>>>>> 0987fd265f1ca3b1710eab3356b13daf274876a7
}
