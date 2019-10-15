package seedu.address.logic.calendar.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.calendar.CalendarCalendarAddressBook;
import seedu.address.model.calendar.CalendarModel;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(CalendarModel calendarModel) {
        requireNonNull(calendarModel);
        calendarModel.setCalendarAddressBook(new CalendarCalendarAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
