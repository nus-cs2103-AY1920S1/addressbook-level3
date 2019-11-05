package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.ImportCommand.MESSAGE_USAGE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import seedu.scheduler.logic.commands.ImportCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.FilePath;
import seedu.scheduler.model.person.Role;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE_PATH);

        if (argMultimap.getValue(PREFIX_FILE_PATH).isEmpty() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        Role type = ParserUtil.parseRole(argMultimap.getPreamble());
        FilePath file = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILE_PATH).get());
        if (!file.exists()) {
            throw new ParseException(ImportCommand.FILE_DOES_NOT_EXIST);
        }
        return new ImportCommand(type, file);
    }
}
