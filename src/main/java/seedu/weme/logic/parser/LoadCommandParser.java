package seedu.weme.logic.parser;

import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;

import seedu.weme.logic.commands.LoadCommand;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.DirectoryPath;

/**
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser implements Parser<LoadCommand> {

    public static final String MESSAGE_MISSING_PATH = "Please provide a directory path to load.";

    /**
     * Parses the given {@code String} of arguments in the context of the LoadCommand
     * and returns an LoadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoadCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH);

        if (argMultimap.getValue(PREFIX_FILEPATH).isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_PATH);
        }

        DirectoryPath path = ParserUtil.parseDirectoryPath(argMultimap.getValue(PREFIX_FILEPATH).get());

        return new LoadCommand(path);
    }

}
