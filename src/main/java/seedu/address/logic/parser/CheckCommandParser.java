package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser to parse check arguments to create checkCommands
 */
public class CheckCommandParser implements Parser<CheckCommand> {
    /**
     * Parses the user input and creates the index for to be used to create the checkcommand
     * @param userInput
     * @return a checkCommand
     * @throws ParseException if the command is in invalid format
     */
    @Override
    public CheckCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new CheckCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Invalid Index Provided"), pe);
        }
    }
}
