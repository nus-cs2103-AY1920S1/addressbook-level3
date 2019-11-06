package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.ExportCommand.MESSAGE_USAGE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import seedu.scheduler.logic.commands.ExportCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.FilePath;

/**
 * Parses user input to create a new ExportCommand object.
 */

public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand.
     * and returns a ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ExportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE_PATH);
        if (argMultimap.getValue(PREFIX_FILE_PATH).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        FilePath file = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILE_PATH).get());
        return new ExportCommand(file);
    }
}
