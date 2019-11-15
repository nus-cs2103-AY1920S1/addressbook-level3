package seedu.pluswork.logic.parser.settings;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.pluswork.logic.commands.settings.ClockCommand;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.settings.ClockFormat;

/**
 * Parses input arguments and creates a new ClockCommand parser object.
 */
public class ClockCommandParser implements Parser<ClockCommand> {

    /**
     * Parses {@code userInput} into a command and returns it.
     *
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
