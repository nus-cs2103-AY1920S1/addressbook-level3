package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DisplayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class DisplayCommandParser implements Parser<DisplayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayCommand
     * and returns a DisplayCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DisplayCommand parse(String userInput) throws ParseException {
        try {
            String type = ParserUtil.parseType(userInput);
            Index index = ParserUtil.parseIndex(userInput);
            return new DisplayCommand(type, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE), pe);
        }
    }
}
