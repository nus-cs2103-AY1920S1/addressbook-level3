//@@author stevenwjy
package tagline.logic.parser.contact;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collections;

import tagline.logic.commands.contact.DeleteContactCommand;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;
import tagline.model.contact.ContactId;

/**
 * Parses input arguments and creates a new DeleteContactCommand object.
 */
public class DeleteContactParser implements Parser<DeleteContactCommand> {
    public static final String DELETE_CONTACT_EMPTY_ID_PROMPT_STRING = "Please enter the ID of the contact to delete.";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteContactCommand
     * and returns a DeleteContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteContactCommand parse(String args) throws ParseException {
        checkCompulsoryFields(args);

        try {
            ContactId contactId = ContactParserUtil.parseContactId(args);
            return new DeleteContactCommand(contactId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteContactCommand.MESSAGE_USAGE), pe);
        }
    }

    //@@author tanlk99
    /**
     * Checks the compulsory fields of the command (i.e. Id).
     * @throws PromptRequestException if a compulsory field is missing
     */
    private void checkCompulsoryFields(String args) throws PromptRequestException {
        if (args.trim().isEmpty()) {
            throw new PromptRequestException(Collections.singletonList(new Prompt("",
                    DELETE_CONTACT_EMPTY_ID_PROMPT_STRING)));
        }
    }
}
