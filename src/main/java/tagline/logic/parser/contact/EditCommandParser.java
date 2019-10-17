package tagline.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_ADDRESS;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_DESCRIPTION;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_EMAIL;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_NAME;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_PHONE;

import tagline.logic.commands.contact.EditContactCommand;
import tagline.logic.commands.contact.EditContactCommand.EditContactDescriptor;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.contact.ContactId;

/**
 * Parses input arguments and creates a new EditContactCommand object
 */
public class EditCommandParser implements Parser<EditContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditContactCommand
     * and returns an EditContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_DESCRIPTION);

        ContactId contactId;

        try {
            contactId = ContactParserUtil.parseContactId(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditContactCommand.MESSAGE_USAGE), pe);
        }

        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editContactDescriptor.setName(ContactParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editContactDescriptor.setPhone(ContactParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editContactDescriptor.setEmail(ContactParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editContactDescriptor
                    .setAddress(ContactParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editContactDescriptor.setDescription(ContactParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditContactCommand.MESSAGE_NOT_EDITED);
        }

        return new EditContactCommand(contactId, editContactDescriptor);
    }
}
