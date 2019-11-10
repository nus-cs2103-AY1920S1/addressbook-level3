package seedu.address.logic.calendar.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.calendar.CalendarAddressBook;
import seedu.address.model.calendar.CalendarModel;

/**
 * Clears all data from Modulo's calendar.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Calendar has been cleared!";


    @Override
    public CommandResult execute(CalendarModel calendarModel) {
        requireNonNull(calendarModel);
        calendarModel.setCalendarAddressBook(new CalendarAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
