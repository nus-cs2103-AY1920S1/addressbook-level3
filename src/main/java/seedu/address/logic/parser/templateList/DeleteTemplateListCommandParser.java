package seedu.address.logic.parser.templateList;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.templateList.DeleteTemplateListCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTemplateListCommand object
 */
public class DeleteTemplateListCommandParser implements Parser<DeleteTemplateListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTemplateListCommand
     * and returns a DeleteTemplateListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTemplateListCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteTemplateListCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTemplateListCommand.MESSAGE_USAGE), pe);
        }
    }

}
