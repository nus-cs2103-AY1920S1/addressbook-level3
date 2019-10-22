package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;

import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import mams.commons.core.Messages;
import mams.commons.core.index.Index;
import mams.logic.commands.AddModCommand;
import mams.logic.commands.ModCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddModCommand object
 */
public class AddModCommandParser implements Parser<AddModCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModCommand
     * and returns an AddModCommand object for execution. (Only argument checking is done here)
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddModCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_MODULE);

        Index index;

        if (argMultimap.getValue(PREFIX_MODULE).isEmpty()) {
            throw new ParseException(ModCommand.MESSAGE_INVALID_MODULE_CODE);
        }

        //priority: Matric > Index
        if (argMultimap.getValue(PREFIX_STUDENT).isEmpty()) {
            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
                return new AddModCommand(index, argMultimap.getAllValues(PREFIX_MODULE).get(0));
            } catch (ParseException pe) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        AddModCommand.MESSAGE_USAGE_ADD_MOD), pe);
            }
        } else if (argMultimap.getValueSize(PREFIX_STUDENT) == 1) {
            return new AddModCommand(argMultimap.getAllValues(PREFIX_STUDENT).get(0),
                    argMultimap.getAllValues(PREFIX_MODULE).get(0));
        } else {
            throw new ParseException(ModCommand.MESSAGE_USAGE_ADD_MOD);
        }
    }
}
