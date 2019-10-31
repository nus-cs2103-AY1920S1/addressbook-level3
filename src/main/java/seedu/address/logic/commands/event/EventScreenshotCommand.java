package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents the event schedule screenshot command
 */
public class EventScreenshotCommand extends EventCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Takes screenshot of schedule\n"
            + "Parameters:\n"
            + "directory/ [DIR]\n"
            + "Example: event screenshot directory/Users/John/Desktop/";
    private static final String SUCCESS_MESSAGE = "Taking screenshot of your schedule";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private String targetFileDirectory;

    public EventScreenshotCommand(String targetFileDirectory) {
        requireNonNull(targetFileDirectory);

        this.targetFileDirectory = targetFileDirectory;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StringBuilder targetFilePathBuilder = new StringBuilder();
        targetFilePathBuilder.append(FILE_SEPARATOR);
        targetFilePathBuilder.append(targetFileDirectory);
        targetFilePathBuilder.append(FILE_SEPARATOR);
        targetFilePathBuilder.append("njoySchedule_");
        targetFilePathBuilder.append(model.getEventScheduleTargetDateTime().toLocalDate().toString());
        return new CommandResult(SUCCESS_MESSAGE, CommandResultType.SCHEDULE_SCREENSHOT,
                targetFilePathBuilder.toString());
    }
}
