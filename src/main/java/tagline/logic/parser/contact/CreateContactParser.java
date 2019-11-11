//@@author stevenwjy
package tagline.logic.parser.contact;

import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_ADDRESS;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_DESCRIPTION;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_EMAIL;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_NAME;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_PHONE;

import java.util.Collections;

import tagline.logic.commands.contact.CreateContactCommand;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;
import tagline.model.contact.Address;
import tagline.model.contact.Contact;
import tagline.model.contact.Description;
import tagline.model.contact.Email;
import tagline.model.contact.Name;
import tagline.model.contact.Phone;

/**
 * Parses input arguments and creates a new CreateContactCommand object.
 */
public class CreateContactParser implements Parser<CreateContactCommand> {
    public static final String ADD_CONTACT_EMPTY_NAME_PROMPT_STRING = "Please enter a name.";

    /**
     * Parses the given {@code String} of arguments in the context of the CreateContactCommand
     * and returns an CreateContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_DESCRIPTION);

        checkCompulsoryFields(argMultimap);

        Name name;
        Phone phone;
        Email email;
        Address address;
        Description description;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ContactParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } else {
            name = ContactParserUtil.parseName("");
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phone = ContactParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        } else {
            phone = ContactParserUtil.parsePhone("");
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            email = ContactParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        } else {
            email = ContactParserUtil.parseEmail("");
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            address = ContactParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        } else {
            address = ContactParserUtil.parseAddress("");
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            description = ContactParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        } else {
            description = ContactParserUtil.parseDescription("");
        }

        Contact contact = new Contact(name, phone, email, address, description);

        return new CreateContactCommand(contact);
    }

    //@@author tanlk99
    /**
     * Checks the compulsory fields of the command (i.e. name).
     * @throws PromptRequestException if a compulsory field is missing
     */
    private void checkCompulsoryFields(ArgumentMultimap argMultimap) throws PromptRequestException {
        if (!argMultimap.getValue(PREFIX_NAME).isPresent()) {
            throw new PromptRequestException(Collections.singletonList(new Prompt(PREFIX_NAME.getPrefix(),
                    ADD_CONTACT_EMPTY_NAME_PROMPT_STRING)));
        }
    }

}
