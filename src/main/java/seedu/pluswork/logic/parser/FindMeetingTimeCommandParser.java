package seedu.pluswork.logic.parser;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_DURATION_HOURS;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_END_PERIOD;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_START_PERIOD;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.logic.commands.FindMeetingTimeCommand;
import seedu.pluswork.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class FindMeetingTimeCommandParser implements Parser<FindMeetingTimeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public static final String INVALID_START_TIME = "The start time has already passed";

    public FindMeetingTimeCommand parse(String args) throws ParseException, FileNotFoundException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_START_PERIOD, PREFIX_END_PERIOD, PREFIX_DURATION_HOURS);

        if (!arePrefixesPresent(argMultimap, PREFIX_START_PERIOD, PREFIX_END_PERIOD, PREFIX_DURATION_HOURS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMeetingTimeCommand.MESSAGE_USAGE));
        }

        LocalDateTime startPeriod = DateTimeUtil.parseDateTime(argMultimap.getValue(PREFIX_START_PERIOD).get());
        LocalDateTime endPeriod = DateTimeUtil.parseDateTime(argMultimap.getValue(PREFIX_END_PERIOD).get());
        Duration durationHours = ParserUtil.parseHours(argMultimap.getValue(PREFIX_DURATION_HOURS).get());
        return new FindMeetingTimeCommand(startPeriod, endPeriod, durationHours);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
