package seedu.elisa.logic.parser;

import static seedu.elisa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.elisa.commons.core.index.Index;
import seedu.elisa.logic.commands.OpenCommand;
import seedu.elisa.logic.parser.exceptions.ParseException;

/**
 * Parses the input to create an OpenCommand with the proper parameters.
 */
public class OpenCommandParser implements Parser<OpenCommand> {

    /**
     * Parses the given {@code description} to generate an OpenCommand with the item at the given index of this list
     * @param description index of item
     * @param flags should be empty
     * @return
     * @throws ParseException if the given string is not a positive integer
     */
    public OpenCommand parse(String description, String flags) throws ParseException {
        // flags should be empty in this case, focus on description only
        try {
            Index index = ParserUtil.parseIndex(description);
            return new OpenCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE), pe);
        }
    }
}
