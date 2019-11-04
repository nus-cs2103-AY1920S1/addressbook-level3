package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.AssignDateCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
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
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignDateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_START_DATE, PREFIX_EVENT_TIME);

        //Ensure fields are compulsory
        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_TIME)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignDateCommand.MESSAGE_USAGE));
        }

        Index eventIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        EventDayTime timePeriod = ParserUtil.parseTimePeriod(argMultimap.getValue(PREFIX_EVENT_TIME).get());

        if (argMultimap.getValue(PREFIX_EVENT_START_DATE).isPresent()) {
            EventDate targetDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_START_DATE).get());
            return new AssignDateCommand(eventIndex, targetDate, timePeriod);
        } else {
            return new AssignDateCommand(eventIndex, timePeriod);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given.
     * Note: Repeated across multiple classes, will refactor later
     * <p>
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
