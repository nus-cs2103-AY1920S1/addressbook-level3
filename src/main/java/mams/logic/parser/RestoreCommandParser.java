package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import static mams.logic.parser.CliSyntax.PREFIX_TAG;

import mams.logic.commands.RestoreCommand;
import mams.logic.commands.SaveCommand;

import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SaveCommand object
 */
public class RestoreCommandParser implements Parser<RestoreCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetCredits
     * and returns an SetCredits object for execution. (Only argument checking is done here)
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RestoreCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (argMultimap.getValue(PREFIX_TAG).isEmpty()) {
            throw new ParseException(SaveCommand.MESSAGE_USAGE_RESTORE);
        } else if (argMultimap.getValue(PREFIX_TAG).get().equals("undo")) {
            throw new ParseException((SaveCommand.MESSAGE_USAGE_RESTORE));
        } else if (argMultimap.getValue(PREFIX_TAG).get().equals("redo")) {
            throw new ParseException(SaveCommand.MESSAGE_USAGE_RESTORE);
        } else if (argMultimap.getValueSize(PREFIX_TAG) == 1) {
            return new RestoreCommand(argMultimap.getAllValues(PREFIX_TAG).get(0));
        } else {
            throw new ParseException(SaveCommand.MESSAGE_USAGE_RESTORE);
        }
    }
}
