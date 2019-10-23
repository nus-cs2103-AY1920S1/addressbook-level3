package seedu.travezy.calendar.commands;

import seedu.travezy.address.logic.commands.Command;
import seedu.travezy.logic.commands.CommandResult;
import seedu.travezy.address.model.AddressBookModel;

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
