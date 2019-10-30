package seedu.address.logic.parser.session;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.insession.SessionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses user input and returns SessionCommand.
 */
public class NewSessionCommandParser implements Parser<SessionCommand> {
    /**
     * Parses the given string {@code userInput} into a SessionCommand and returns it.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public SessionCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionCommand.MESSAGE_USAGE));
        }

        Name compName = ParserUtil.parseName(trimmedInput);
        return new SessionCommand(compName);
    }
}
