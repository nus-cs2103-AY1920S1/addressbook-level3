package seedu.address.logic.commands.event;

import java.io.IOException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a view events command.
 */
public class EventViewCommand extends EventCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " view: your events in a calendar";
    public static final String MESSAGE_SUCCESS = "Showing your schedule for the week";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_SUCCESS, CommandResultType.SHOW_SCHEDULE);
    }
}


