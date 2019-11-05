package seedu.scheduler.logic.commands;

import java.io.IOException;

import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.FilePath;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.util.CsvWriter;

/**
 * Exports schedules to target .csv file.
 */

public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": export schedules to specified .csv file. \n"
            + "Example: " + COMMAND_WORD + "<FULL_FILE_PATH>";
    public static final String SUCCESS_MESSAGE = "Data exported successfully.";
    public static final String NOT_SCHEDULED_ERROR = "Interview slots have not been scheduled. Please ensure that "
            + "data has been imported and 'schedule' command has been ran.";
    public static final String ERROR_MESSAGE = "Could not write to file.";

    private FilePath destinationFile;

    /**
     * Constructor for ExportCommand.
     * @param file destination file.
     */
    public ExportCommand(FilePath file) {
        this.destinationFile = file;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            if (!model.isScheduled()) {
                throw new CommandException(NOT_SCHEDULED_ERROR);
            }
            CsvWriter writer = new CsvWriter(destinationFile.getValue(), model);
            writer.writeSchedulesToFile();
            return new CommandResult(SUCCESS_MESSAGE);
        } catch (IOException ioe) {
            throw new CommandException(ERROR_MESSAGE, ioe);
        }
    }
}
