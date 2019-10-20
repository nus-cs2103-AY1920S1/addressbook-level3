package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ScheduleActivityCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.day.time.DurationInHalfHour;
import seedu.address.model.day.time.TimeInHalfHour;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser {

    private static final Pattern ADD_COMMAND_FORMAT = Pattern.compile("(?<type>activity)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleActivityCommand
     * and returns a ScheduleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        final Matcher matcher = ADD_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String type = matcher.group("type");
        final String arguments = matcher.group("arguments");

        if (type.equals(ScheduleActivityCommand.SECOND_COMMAND_WORD)) {
            return parseActivity(arguments);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddActivityCommand for a Activity
     * and returns an AddActivityCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private ScheduleActivityCommand parseActivity(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_START_TIME,
                PREFIX_DURATION, PREFIX_DAY);

        Index activityIndex;

        try {
            activityIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ScheduleActivityCommand.MESSAGE_USAGE), pe);
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_START_TIME, PREFIX_DURATION, PREFIX_DAY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ScheduleActivityCommand.MESSAGE_USAGE));
        }

        TimeInHalfHour startTime = ParserUtil.parseTimeInHalfHour(argMultimap.getValue(PREFIX_START_TIME).get());
        DurationInHalfHour duration = ParserUtil.parseDurationInHalfHour(argMultimap.getValue(PREFIX_DURATION).get());
        Index dayIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_DAY).get());

        return new ScheduleActivityCommand(activityIndex, startTime, duration, dayIndex);
    }
}
