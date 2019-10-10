package tagline.logic.parser.contact;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tagline.commons.core.index.Index;
import tagline.logic.commands.contact.DeleteContactCommand;
import tagline.logic.parser.Parser;
import tagline.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteContactCommand object
 */
public class DeleteContactParser implements Parser<DeleteContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteContactCommand
     * and returns a DeleteContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteContactCommand parse(String args) throws ParseException {
        try {
            Index index = ContactParserUtil.parseIndex(args);
            return new DeleteContactCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteContactCommand.MESSAGE_USAGE), pe);
        }
    }

}
