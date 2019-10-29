package seedu.address.logic.parser.session;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.session.AttemptLiftedCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user arguments and returns AttemptLiftedCommand.
 */
public class AttemptLiftedCommandParser implements Parser<AttemptLiftedCommand> {
    /**
     * Parses {@code userInput} into an AttemptLiftedCommand and returns it.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public AttemptLiftedCommand parse(String userInput) throws ParseException {
        String trimmedUserInput = userInput.trim();
        Boolean isSuccess = null;

        if (trimmedUserInput.isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AttemptLiftedCommand.MESSAGE_USAGE));
        }

        if (trimmedUserInput.equals("Y") || trimmedUserInput.equals("y")) {
            isSuccess = true;
        }

        if (trimmedUserInput.equals("N") || trimmedUserInput.equals("n")) {
            isSuccess = false;
        }

        if (isSuccess == null) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AttemptLiftedCommand.MESSAGE_USAGE));
        }

        return new AttemptLiftedCommand(isSuccess);
    }
}
