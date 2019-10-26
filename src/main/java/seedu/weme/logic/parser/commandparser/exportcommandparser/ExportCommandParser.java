package seedu.weme.logic.parser.commandparser.exportcommandparser;

import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;

import seedu.weme.logic.commands.exportcommand.ExportCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ArgumentMultimap;
import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.parser.util.ParserUtil;
import seedu.weme.model.DirectoryPath;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    public static final String MESSAGE_MISSING_PATH = "Please provide a directory path for export.";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH);

        if (argMultimap.getValue(PREFIX_FILEPATH).isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_PATH);
        }

        DirectoryPath path = ParserUtil.parseDirectoryPath(argMultimap.getValue(PREFIX_FILEPATH).get());

        return new ExportCommand(path);
    }

}
