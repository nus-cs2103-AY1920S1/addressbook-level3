package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.mark.logic.commands.ImportCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ImportCommand} object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // args must contain exactly one word
        if (trimmedArgs.isEmpty() || trimmedArgs.split(" ").length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        Path sourceFile = Paths.get("data", "bookmarks", trimmedArgs);

        return new ImportCommand(sourceFile);
    }
}
