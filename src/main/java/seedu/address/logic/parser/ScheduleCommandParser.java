package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 * @@author oscarsu97
 */
public class ScheduleCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns a ScheduleActivityCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_START_TIME, PREFIX_DAY);

        if (!arePrefixesPresent(argMultimap, PREFIX_START_TIME, PREFIX_DAY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ScheduleCommand.MESSAGE_USAGE));
        }
        try {
            Index activityIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
            Index dayIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_DAY).get());

            return new ScheduleCommand(activityIndex, startTime, dayIndex);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ScheduleCommand.MESSAGE_USAGE), pe);
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
