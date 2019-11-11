package seedu.address.model.calendar;

import javafx.collections.ObservableList;
import seedu.address.model.calendar.task.Task;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyCalendarAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Task> getPersonList();

}
