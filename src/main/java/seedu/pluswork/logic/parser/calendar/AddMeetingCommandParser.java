package seedu.pluswork.logic.parser.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEETING_INDEX;

import java.util.stream.Stream;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.calendar.AddMeetingCommand;
import seedu.pluswork.logic.parser.ArgumentMultimap;
import seedu.pluswork.logic.parser.ArgumentTokenizer;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.Prefix;
import seedu.pluswork.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DoneTaskCommand object
 */
public class AddMeetingCommandParser implements Parser<AddMeetingCommand> {
    public static final String MESSAGE_NO_ID = "Please enter the index of the meeting you want to add.";

    /**
     * Parses the given {@code String} of arguments in the context of the DoneTask
     * and returns a DoneTask object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMeetingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEETING_INDEX);

        Index meetingIndex;

        if (!arePrefixesPresent(argMultimap, PREFIX_MEETING_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        try {
            meetingIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MEETING_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE), pe);
        }

        return new AddMeetingCommand(meetingIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
