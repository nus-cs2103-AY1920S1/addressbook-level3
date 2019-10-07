package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.GotoContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.View;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class GotoContactsCommandParser implements Parser<GotoContactsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GotoContactsCommand
     * and returns a GotoContactsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GotoContactsCommand parse(String args) throws ParseException {
        try {
            View view  = ParserUtil.parseView(args);
            return new GotoContactsCommand(view);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
