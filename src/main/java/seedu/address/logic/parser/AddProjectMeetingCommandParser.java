package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddProjectMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.*;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddProjectMeetingCommandParser implements Parser<AddProjectMeetingCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddProjectMeetingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TIME, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_TIME, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProjectMeetingCommand.MESSAGE_USAGE));
        }

        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        Description description = ParserUtil.parseMeetingDescription((argMultimap.getValue(PREFIX_DESCRIPTION).get()));

        Meeting meeting = new Meeting(time, description);

        return new AddProjectMeetingCommand(meeting);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
