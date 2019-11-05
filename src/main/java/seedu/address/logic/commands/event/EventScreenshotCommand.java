package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

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
    private static final String SUCCESS_MESSAGE = "Successfully saved screenshot of your schedule";

    public EventScreenshotCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(SUCCESS_MESSAGE, CommandResultType.SCHEDULE_SCREENSHOT,
                model.getEventSchedulePrefString());
    }
}
