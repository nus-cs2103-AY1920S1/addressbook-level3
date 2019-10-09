package seedu.address.calendar.commands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Shows calendar.
 */
public class CalendarCommand extends Command {
    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows you your calendar.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_CALENDAR_MESSAGE = "Opened calendar page.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_CALENDAR_MESSAGE, false, false);
    }
}