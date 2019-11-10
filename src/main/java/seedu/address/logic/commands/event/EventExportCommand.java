package seedu.address.logic.commands.event;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a export events command. With purpose to generate corresponding command result type.
 */
public class EventExportCommand extends EventCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports your events to a ICS file\n"
            + "Example: event export";
    public static final String MESSAGE_EXPORT_SUCCESS = "Events successfully export. Check the export directory"
            + " for your schedule .ics export.";

    public EventExportCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_EXPORT_SUCCESS, CommandResultType.EXPORT_CALENDAR);
    }
}


