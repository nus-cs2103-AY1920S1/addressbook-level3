package seedu.address.calendar.commands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBookModel;

/**
 * Shows calendar.
 */
public class CalendarCommand extends Command {
    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows you your calendar.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_CALENDAR_MESSAGE = "calendar";

    @Override
    public CommandResult execute(AddressBookModel addressBookModel) {
        return new CommandResult(SHOWING_CALENDAR_MESSAGE, false, false, true);
    }
}
