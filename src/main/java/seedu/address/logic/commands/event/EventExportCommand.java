package seedu.address.logic.commands.event;

import java.io.IOException;

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
    public static final String MESSAGE_SUCCESS = "Exported your events to ";
    private final String directoryPath;

    public EventExportCommand(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            String fullFilePath = model.saveToICSFile(directoryPath);
            return new CommandResult(generateSuccessMessage(fullFilePath),
                    CommandResultType.SHOW_SCHEDULE);
        } catch (IOException ex) {
            return new CommandResult(generateFailureMessage(directoryPath), CommandResultType.SHOW_SCHEDULE);
        }
    }

    private String generateSuccessMessage(String fullFilePath) {
        return String.format("Events successfully exported to: %s", fullFilePath);
    }

    private String generateFailureMessage(String directoryPath) {
        return String.format("Events failed to export to directory: %s\n Verify the filePath is valid.", directoryPath);
    }
}


