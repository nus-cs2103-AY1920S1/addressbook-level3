package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CancelAppCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


/**
 * Parses input arguments and creates a new CancelAppCommand object
 */
public class CancelAppCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the CancelApptCommand
     * and returns an CancelApptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CancelAppCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CancelAppCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelAppCommand.MESSAGE_USAGE), e);
        }
    }
}
