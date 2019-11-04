package seedu.address.logic.commands.event;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a view events command.
 */
public class EventExportCommand extends EventCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports your events to a ICS file\n"
            + "Example: event export";

    public EventExportCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(generateSuccessMessage(), CommandResultType.EXPORT_CALENDAR);
    }

    private String generateSuccessMessage() {
        return String.format("Events successfully exported");
    }
}


