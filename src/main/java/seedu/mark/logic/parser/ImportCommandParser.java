package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.ParserUtil.MESSAGE_FILE_NAME_INCLUDES_EXTENSION;
import static seedu.mark.logic.parser.ParserUtil.MESSAGE_INVALID_FILE_NAME;

import java.nio.file.Path;

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

        if (trimmedArgs.isEmpty() || trimmedArgs.split(" ").length != 1) {
            // args must contain exactly one word
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        } else if (trimmedArgs.endsWith(".json")) {
            throw new ParseException(MESSAGE_FILE_NAME_INCLUDES_EXTENSION);
        } else if (!ParserUtil.isValidFilename(trimmedArgs)) {
            throw new ParseException(MESSAGE_INVALID_FILE_NAME);
        }

        Path sourceFile = Path.of("data", "bookmarks", trimmedArgs + ".json");

        return new ImportCommand(sourceFile);
    }
}
