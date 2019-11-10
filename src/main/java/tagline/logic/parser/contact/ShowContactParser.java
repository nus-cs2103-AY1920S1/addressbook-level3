package tagline.logic.parser.contact;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collections;

import tagline.logic.commands.contact.ShowContactCommand;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;
import tagline.model.contact.ContactId;

/**
 * Parses input arguments and creates a new ShowContactCommand object.
 */
public class ShowContactParser implements Parser<ShowContactCommand> {
    public static final String SHOW_CONTACT_EMPTY_ID_PROMPT_STRING = "Please enter the ID of the contact to show.";

    /**
     * Parses the given {@code String} of arguments in the context of the ShowContactCommand.
     * and returns a ShowContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowContactCommand parse(String args) throws ParseException {
        checkCompulsoryFields(args);

        try {
            ContactId contactId = ContactParserUtil.parseContactId(args);
            return new ShowContactCommand(contactId);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowContactCommand.MESSAGE_USAGE), pe);
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
                    SHOW_CONTACT_EMPTY_ID_PROMPT_STRING)));
        }
    }
}
