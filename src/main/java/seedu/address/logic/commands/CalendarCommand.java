package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = "calendar" + ": Jumps to a specified date."
            + "Specify month and year to view calendar for that month. Specify day, month and "
            + "year to view details for that date.\n"
            + "Parameters: MMYYYY / DDMMYYYY\n"
            + "Examples:\n"
            + "calendar 062019\n"
            + "calendar 09062019";

    public static final String MESSAGE_SUCCESS_1 = "Viewing calendar for: ";
    public static final String MESSAGE_SUCCESS_2 = "Viewing details for: ";

    public CalendarCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
