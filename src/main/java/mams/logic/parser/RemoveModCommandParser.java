package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_INDEX;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;

import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import mams.logic.commands.ModCommand;
import mams.logic.commands.RemoveModCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveModCommand object
 */
public class RemoveModCommandParser implements Parser<RemoveModCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveModCommand
     * and returns an RemoveModCommand object for execution. (Only argument checking is done here)
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RemoveModCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_MODULE, PREFIX_INDEX);

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            if (argMultimap.getValue(PREFIX_INDEX).isPresent()) {
                return new RemoveModCommand.RemoveModCommandBuilder(argMultimap.getAllValues(PREFIX_MODULE).get(0),
                        true)
                        .setIndex(argMultimap.getAllValues(PREFIX_INDEX).get(0)).build();
            } else if (argMultimap.getValue(PREFIX_STUDENT).isPresent()) {
                return new RemoveModCommand.RemoveModCommandBuilder(argMultimap.getAllValues(PREFIX_MODULE).get(0),
                        false)
                        .setMatricId(argMultimap.getAllValues(PREFIX_STUDENT).get(0)).build();
            } else {
                throw new ParseException(ModCommand.MESSAGE_MISSING_MATRICID_OR_INDEX);
            }
        } else {
            throw new ParseException(ModCommand.MESSAGE_USAGE_REMOVE_MOD);
        }
    }
}
