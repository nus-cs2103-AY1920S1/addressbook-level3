package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AttendanceCommand object
 */
public class AttendanceCommandParser implements Parser<AttendanceCommand> {

    /**
     * Parses the input arguments and returns an AttendanceCommand object.
     * @param userInput Parsed user arguments.
     * @return AttendanceCommand with index.
     * @throws ParseException Thrown when command format is invalid.
     */
    @Override
    public AttendanceCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new AttendanceCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE),
                    pe);
        }
    }
}
