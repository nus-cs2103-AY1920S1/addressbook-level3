package seedu.address.logic.commands.csvcommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.csvcommand.csvutil.CsvUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports Alfred data into an external CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export"; // or any other suggestions
    public static final String MESSAGE_SUCCESS = "Exported all data to %s"; // %s -> file name
    public static final String MESSAGE_IO_EXCEPTION =
            "Something went wrong while accessing your file! Please try again...";
    public static final String MESSAGE_INVALID_PATH_EXCEPTION =
            "Invalid file path: %s - Exported all data to %s"; // %s -> this.csvFilePath
    public static final String MESSAGE_EMPTY_DATA = "No data to export. File was not created.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": exports Alfred data to a CSV file.\n"
            + "Format: " + COMMAND_WORD + " [ENTITY] [" + PREFIX_FILE_PATH + "FILE_PATH]\n"
            + "\tExample 1: " + COMMAND_WORD
            + " (Creates AlfredData/Alfred_Entity_List.csv at current working directory)\n"
            + "\tExample 2 (Windows): " + COMMAND_WORD + " " + PREFIX_FILE_PATH + "C:/Users/USER"
            + " (Creates Alfred_Entity_List.csv at C:/Users/USER)"; // TODO: Add other examples on different platforms
    public static final String ASSERTION_FAILED_NOT_CSV = "File given is not a CSV file.";
    public static final Path DEFAULT_FILE_PATH = Paths.get("AlfredData", "Alfred_Data.csv");

    protected Path csvFilePath;
    protected String messageSuccess;

    public ExportCommand(String csvFilePath) {
        assert csvFilePath.isBlank() || csvFilePath.toLowerCase().endsWith(".csv") : ASSERTION_FAILED_NOT_CSV;
        // If either filePath was not specified, go with default values
        if (csvFilePath.isBlank()) {
            csvFilePath = DEFAULT_FILE_PATH.toString();
        }
        try {
            this.csvFilePath = Path.of(csvFilePath);
            this.messageSuccess = String.format(MESSAGE_SUCCESS, csvFilePath);
        } catch (InvalidPathException ipe) {
            this.csvFilePath = DEFAULT_FILE_PATH;
            this.messageSuccess = String.format(MESSAGE_INVALID_PATH_EXCEPTION, csvFilePath, this.csvFilePath);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_DATA);
        }
        try {
            File csvFile = this.csvFilePath.toFile();
            FileUtil.createIfMissing(this.csvFilePath);
            CsvUtil.writeToCsv(csvFile, model);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_IO_EXCEPTION);
        }
        return new CommandResult(this.messageSuccess);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ExportCommand)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        ExportCommand command = (ExportCommand) other;
        return this.csvFilePath.equals(command.csvFilePath);
    }

}
