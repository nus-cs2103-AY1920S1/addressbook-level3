package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DAY;

import java.util.stream.Stream;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.schedulecommand.UnscheduleCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 * @author 1nefootstep
 */
public class UnscheduleCommandParser implements Parser<UnscheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnscheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnscheduleCommand.MESSAGE_USAGE));
        }

        try {
            Index activityIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            Index dayIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_DAY).get());
            return new UnscheduleCommand(activityIndex, dayIndex, false);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnscheduleCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
