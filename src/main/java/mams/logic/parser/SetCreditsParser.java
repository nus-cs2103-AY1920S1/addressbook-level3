package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import static mams.logic.parser.CliSyntax.PREFIX_CREDITS;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import mams.commons.core.Messages;
import mams.commons.core.index.Index;
import mams.logic.commands.SetCredits;
import mams.logic.commands.StudentCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddModCommand object
 */
public class SetCreditsParser implements Parser<SetCredits> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModCommand
     * and returns an AddModCommand object for execution. (Only argument checking is done here)
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SetCredits parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_CREDITS);

        Index index;

        if (argMultimap.getValue(PREFIX_CREDITS).isEmpty()) {
            throw new ParseException(StudentCommand.MESSAGE_INVALID_CREDIT_VALUE);
        }

        //priority: Matric > Index
        if (argMultimap.getValue(PREFIX_STUDENT).isEmpty()) {
            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
                return new SetCredits(index, argMultimap.getAllValues(PREFIX_CREDITS).get(0));
            } catch (ParseException pe) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        SetCredits.MESSAGE_USAGE_SETCREDITS), pe);
            }
        } else if (argMultimap.getValueSize(PREFIX_STUDENT) == 1) {
            return new SetCredits(argMultimap.getAllValues(PREFIX_STUDENT).get(0),
                    argMultimap.getAllValues(PREFIX_CREDITS).get(0));
        } else {
            throw new ParseException(StudentCommand.MESSAGE_USAGE_SETCREDITS);
        }
    }
}
