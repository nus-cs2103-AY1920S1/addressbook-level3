package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GenerateSlotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timetable.TimeRange;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GenerateSlotCommand.PREFIX_DURATION;
import static seedu.address.logic.commands.GenerateSlotCommand.PREFIX_TIMERANGE;

public class GenerateSlotCommandParser implements Parser<GenerateSlotCommand> {
    @Override
    public GenerateSlotCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DURATION, PREFIX_TIMERANGE);
        if (argMultimap.getValue(PREFIX_DURATION).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenerateSlotCommand.MESSAGE_USAGE));
        }
        TimeRange timeRangeSpecified;
        if (argMultimap.getValue(PREFIX_TIMERANGE).isPresent()) {
            timeRangeSpecified = ParserUtil.parseTimeRange(argMultimap.getValue(PREFIX_TIMERANGE).get());
        } else {
            try {
                timeRangeSpecified = new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("00:00"), DayOfWeek.SUNDAY, LocalTime.parse("23:59"));
            } catch (IllegalValueException e) {
                // Should never reach this place.
                e.printStackTrace();
                timeRangeSpecified = null;
            }
        }
        return new GenerateSlotCommand(
                Integer.parseInt(argMultimap.getValue(PREFIX_DURATION).get()),
                timeRangeSpecified);
    }
}
