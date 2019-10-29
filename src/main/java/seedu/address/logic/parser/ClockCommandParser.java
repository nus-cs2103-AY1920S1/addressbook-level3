package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ClockCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.settings.ClockFormat;

/**
 * Parses input arguments and creates a new ClockCommand parser object.
 */
public class ClockCommandParser implements Parser<ClockCommand> {

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ClockCommand parse(String userInput) throws ParseException {
        ClockFormat clockFormat;

        try {
            clockFormat = ParserUtil.parseClock(userInput);
            return new ClockCommand(clockFormat);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClockCommand.MESSAGE_USAGE), pe);
        }
    }
}
