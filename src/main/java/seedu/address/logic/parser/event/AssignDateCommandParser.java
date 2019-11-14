package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_START_AFTER_END;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.Prefix.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.AssignDateCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDayTime;

/**
 * Parses input arguments and creates a new AssignDateCommand object.
 */
public class AssignDateCommandParser implements Parser<AssignDateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignDateCommand
     * and returns an AssignDateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AssignDateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_START_DATE, PREFIX_EVENT_END_DATE, PREFIX_EVENT_TIME);

        boolean startOrTargetDateStated = argMultimap.getValue(PREFIX_EVENT_START_DATE).isPresent();
        boolean endDateStated = argMultimap.getValue(PREFIX_EVENT_END_DATE).isPresent();

        //Ensure fields are compulsory
        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_TIME) || argMultimap.getPreamble().isEmpty()
                || (!startOrTargetDateStated && endDateStated)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignDateCommand.MESSAGE_USAGE));
        }

        Index eventIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        EventDayTime timePeriod = ParserUtil.parseTimePeriod(argMultimap.getValue(PREFIX_EVENT_TIME).get());

        if (startOrTargetDateStated && endDateStated) {
            EventDate startDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_START_DATE).get());
            EventDate endDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_END_DATE).get());
            if (startDate.isAfter(endDate)) {
                throw new ParseException(String.format(MESSAGE_DATE_START_AFTER_END, startDate, endDate));
            }

            return new AssignDateCommand(eventIndex, startDate, endDate, timePeriod);
        } else if (startOrTargetDateStated) {
            EventDate targetDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_START_DATE).get());
            return new AssignDateCommand(eventIndex, targetDate, timePeriod);
        } else {
            return new AssignDateCommand(eventIndex, timePeriod);
        }
    }

}
