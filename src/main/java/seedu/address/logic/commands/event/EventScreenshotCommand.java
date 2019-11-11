package seedu.address.logic.commands.event;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents the event schedule screenshot command
 */
public class EventScreenshotCommand extends EventCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Takes screenshot of schedule\n"
            + "Example: event screenshot";
    public static final String MESSAGE_SCREENSHOT_SUCCESS = "Successfully saved screenshot of your schedule."
            + " Check the printable directory for your png file.";

    public EventScreenshotCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_SCREENSHOT_SUCCESS, CommandResultType.SCHEDULE_SCREENSHOT,
                model.getEventSchedulePrefString());
    }
}
