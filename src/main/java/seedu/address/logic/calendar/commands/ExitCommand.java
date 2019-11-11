package seedu.address.logic.calendar.commands;

import seedu.address.model.calendar.CalendarModel;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Calendar as requested ...";

    @Override
    public CommandResult execute(CalendarModel calendarModel) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
