package tagline.logic.parser.contact;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tagline.logic.commands.contact.ShowContactCommand;
import tagline.logic.parser.Parser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.contact.ContactId;

/**
 * Parses input arguments and creates a new DeleteContactCommand object
 */
public class ShowContactParser implements Parser<ShowContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowContactCommand.
     * and returns a ShowContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowContactCommand parse(String args) throws ParseException {
        try {
            ContactId contactId = ContactParserUtil.parseContactId(args);
            return new ShowContactCommand(contactId);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowContactCommand.MESSAGE_USAGE), pe);
        }
    }

}
