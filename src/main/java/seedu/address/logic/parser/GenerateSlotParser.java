package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GenerateSlotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timetable.TimeTable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GenerateSlotCommand.*;

public class GenerateSlotParser implements Parser<GenerateSlotCommand> {
    @Override
    public GenerateSlotCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_TIMETABLE, PREFIX_DURATION, PREFIX_TIMERANGE);
        List<TimeTable> timeTables = new ArrayList<>();
        if (argMultimap.getValue(PREFIX_TIMETABLE).isEmpty() || argMultimap.getValue(PREFIX_DURATION).isEmpty()
                || argMultimap.getValue(PREFIX_TIMERANGE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenerateSlotCommand.MESSAGE_USAGE));
        }
        try {
            timeTables.add(new TimeTable(argMultimap.getValue(PREFIX_TIMETABLE).get()));
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_TIMETABLE_FORMAT));
        }
        return new GenerateSlotCommand(
                timeTables,
                Integer.parseInt(argMultimap.getValue(PREFIX_DURATION).get()),
                ParserUtil.parseTimeRange(argMultimap.getValue(PREFIX_TIMERANGE).get()));
    }
}
