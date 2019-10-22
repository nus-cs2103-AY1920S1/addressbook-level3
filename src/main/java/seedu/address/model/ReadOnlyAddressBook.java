package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons, earnings and commands list.
     * This list will not contain any duplicate persons, earnings or commands.
     */
    ObservableList<Person> getPersonList();
    ObservableList<Earnings> getEarningsList();
    ObservableList<CommandObject> getCommandsList();
    ObservableList<Reminder> getReminderList();

}
