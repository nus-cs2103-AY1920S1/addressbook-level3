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
        return new CommandResult(SUCCESS_MESSAGE, CommandResultType.SCHEDULE_SCREENSHOT,
                formatTargetFilePath(targetFileDirectory, model.getEventScheduleTargetDateTime()));
    }

    /**
     * Formats the absolute file path of the screenshot.
     * @param targetFileDirectory target directory to save the screenshot
     * @param targetDateTime target date time of the event schedule
     * @return A string representing the absolute file path of the screenshot to be saved to.
     */
    private String formatTargetFilePath(String targetFileDirectory, LocalDateTime targetDateTime) {
        StringBuilder targetFilePathBuilder = new StringBuilder();
        targetFilePathBuilder.append(FILE_SEPARATOR);
        targetFilePathBuilder.append(targetFileDirectory);
        targetFilePathBuilder.append(FILE_SEPARATOR);
        targetFilePathBuilder.append("njoyScreenshot_");
        targetFilePathBuilder.append(targetDateTime.toLocalDate().toString());
        return targetFilePathBuilder.toString();
    }
}
