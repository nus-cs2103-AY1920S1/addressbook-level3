package seedu.algobase.logic.parser.storage;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PATH;

import seedu.algobase.commons.util.FileUtil.Format;
import seedu.algobase.logic.commands.storage.ImportCommand;
import seedu.algobase.logic.parser.ArgumentMultimap;
import seedu.algobase.logic.parser.ArgumentTokenizer;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_FORMAT, PREFIX_PATH);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_FORMAT)
            || !ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PATH)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        try {
            Format format = ParserUtil.parseFileFormat(argMultimap.getValue(PREFIX_FORMAT).get());
            String path = argMultimap.getValue(PREFIX_PATH).get();
            return new ImportCommand(format, path);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE), pe);
        }
    }

}
