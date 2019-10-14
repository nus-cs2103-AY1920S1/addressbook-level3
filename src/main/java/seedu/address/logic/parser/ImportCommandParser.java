package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser class for ImportCommand.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns a ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return new ImportCommand(trimmedArgs);
    }
}
