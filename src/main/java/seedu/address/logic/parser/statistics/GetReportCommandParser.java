package seedu.address.logic.parser.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.statistics.GetReportCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GetReportCommand object.
 */
public class GetReportCommandParser implements Parser<GetReportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetReportCommand
     * and returns an GetReportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public GetReportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        int index;
        try {
            index = Integer.parseInt(args.trim());
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetReportCommand.MESSAGE_USAGE), e);
        }
        return new GetReportCommand(index);
    }
}
