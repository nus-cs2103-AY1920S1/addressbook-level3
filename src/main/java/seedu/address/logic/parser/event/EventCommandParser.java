package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATETIME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EventAddCommand;
import seedu.address.logic.commands.event.EventCommand;
import seedu.address.logic.commands.question.QuestionEditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new {@code EventCommand} object
 */
public class EventCommandParser implements Parser<EventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EventAddCommand
     * and returns an EventAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer
            .tokenize(args, PREFIX_EVENT,
                    PREFIX_EVENTNAME,
                    PREFIX_STARTDATETIME,
                    PREFIX_ENDDATETIME,
                    PREFIX_RECUR,
                    PREFIX_COLOR);

        boolean isEdit = false;
        Index index = Index.fromZeroBased(0);
        try {
            String preamble = argMultimap.getPreamble();

            if (!preamble.isBlank()) {
                index = ParserUtil.parseIndex(preamble);
                isEdit = true;
            }
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuestionEditCommand.MESSAGE_USAGE),
                pe);
        }
        return addCommand(argMultimap);

    }

    /**
     * Performs validation and return the EventAddCommand object.
     *
     * @param argMultimap for tokenized input.
     * @return EventAddCommand object.
     * @throws ParseException
     */
    private EventAddCommand addCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_EVENTNAME, PREFIX_STARTDATETIME,
                PREFIX_ENDDATETIME, PREFIX_RECUR, PREFIX_COLOR)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String
                    .format(MESSAGE_INVALID_COMMAND_FORMAT, EventAddCommand.MESSAGE_USAGE));
        }

        String eventName = argMultimap.getValue(PREFIX_EVENTNAME).orElse("");
        String startDateTime = argMultimap.getValue(PREFIX_STARTDATETIME).orElse("");
        String endDateTime = argMultimap.getValue(PREFIX_ENDDATETIME).orElse("");
        String recurType = argMultimap.getValue(PREFIX_RECUR).orElse("");
        String colorNumber = argMultimap.getValue(PREFIX_COLOR).orElse("");

        return new EventAddCommand(eventName, startDateTime, endDateTime, recurType, colorNumber);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
        Prefix... prefixes) {
        return Stream.of(prefixes)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
