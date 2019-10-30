package seedu.system.logic.parser.insession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.system.logic.commands.outofsession.StartSessionCommand;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.person.Name;

/**
 * Parses user input and returns StartSessionCommand.
 */
public class NewSessionCommandParser implements Parser<StartSessionCommand> {
    /**
     * Parses the given string {@code userInput} into a StartSessionCommand and returns it.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public StartSessionCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartSessionCommand.MESSAGE_USAGE));
        }

        Name compName = ParserUtil.parseName(trimmedInput);
        return new StartSessionCommand(compName);
    }
}
