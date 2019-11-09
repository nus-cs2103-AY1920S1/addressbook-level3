package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.path.FolderPath;

/**
 * Exports all spending to the filepath.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports all spending from the spending list to the folder. "
            + "Parameters: "
            + PREFIX_PATH + "FOLDERPATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATH + "C:\\Users\\User\\Documents";

    public static final String MESSAGE_SUCCESS = "Exported all spending to %s";

    private static final Logger logger = LogsCenter.getLogger(ExportCommand.class);

    private final FolderPath fullFolderPath;

    public ExportCommand(FolderPath folderPath) {
        requireNonNull(folderPath);
        fullFolderPath = folderPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {

            JsonNode jsonTree = new ObjectMapper().readTree(model.getSpendingBookFilePath().toFile()).get("spendings");
            Builder csvSchemaBuilder = CsvSchema.builder();
            JsonNode firstObject = jsonTree.elements().next();
            firstObject.fieldNames().forEachRemaining(fieldName -> {
                csvSchemaBuilder.addColumn(fieldName);
            });
            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

            CsvMapper csvMapper = new CsvMapper();
            File file;
            if (fullFolderPath.toString().endsWith(".csv")) {
                file = new File(fullFolderPath.toString());
            } else {
                file = new File(fullFolderPath.toString() + "/moneygowhere.csv");
            }
            csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValue(file, jsonTree);
            logger.log(Level.INFO, String.format(MESSAGE_SUCCESS, file.getAbsolutePath()));
            return new CommandResult(String.format(MESSAGE_SUCCESS, file.getAbsolutePath()));
        } catch (NoSuchElementException ex) {
            throw new CommandException(ex.getMessage());
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && fullFolderPath.equals(((ExportCommand) other).fullFolderPath));
    }
}
