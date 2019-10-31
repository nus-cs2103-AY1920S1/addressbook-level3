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
            + "Parameters:\n"
            + "directory/ [DIR]\n"
            + "Example: event export directory/Users/John/Desktop";
    private final String directoryPath;

    public EventExportCommand(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setEventExportPath(directoryPath);
        return new CommandResult(generateSuccessMessage(directoryPath), CommandResultType.EXPORT_CALENDAR);
    }

    private String generateSuccessMessage(String fullFilePath) {
        return String.format("Events successfully exported to: %s", fullFilePath);
    }
}


