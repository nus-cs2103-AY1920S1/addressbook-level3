package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.ClearDateMappingCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearDateMappingCommand object
 */
public class ClearDateMappingCommandParser implements Parser<ClearDateMappingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearDateMappingCommand
     * and returns a ClearDateMappingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearDateMappingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ClearDateMappingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearDateMappingCommand.MESSAGE_USAGE), pe);
        }
    }

}
