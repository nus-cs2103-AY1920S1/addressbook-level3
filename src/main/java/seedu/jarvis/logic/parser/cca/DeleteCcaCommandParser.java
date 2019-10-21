package seedu.jarvis.logic.parser.cca;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.cca.DeleteCcaCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CcaAddressCommand object
 */
public class DeleteCcaCommandParser implements Parser<DeleteCcaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAddressCommand
     * and returns a DeleteAddressCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCcaCommand parse(String args) throws ParseException {
        try {
            Index index = CcaParserUtil.parseIndex(args);
            return new DeleteCcaCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCcaCommand.MESSAGE_USAGE), pe);
        }
    }

}
