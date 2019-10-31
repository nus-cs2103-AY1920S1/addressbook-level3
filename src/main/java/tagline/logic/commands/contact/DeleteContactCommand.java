package tagline.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static tagline.model.contact.ContactModel.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.Optional;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactId;

/**
 * Deletes a contact identified using it's displayed index from the address book.
 */
public class DeleteContactCommand extends ContactCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_KEY + " " + COMMAND_WORD
            + ": Deletes the contact identified by the index number used in the displayed contact list.\n"
            + "Parameters: CONTACT_ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";

    public static final String MESSAGE_NON_EXISTING_ID = "Wrong contact ID.";

    private final ContactId contactId;

    public DeleteContactCommand(ContactId contactId) {
        this.contactId = contactId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Contact> contact = model.findContact(contactId);
        if (contact.isEmpty()) {
            throw new CommandException(MESSAGE_NON_EXISTING_ID);
        }

        Contact contactToDelete = contact.get();
        model.deleteContact(contactToDelete);

        // the following is to cater for the edge case when you delete a contact in ONE_GROUP view
        // forces it to change to just contact list instead of sticking to one_group
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        return new CommandResult(String.format(MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete), ViewType.CONTACT_LIST);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteContactCommand // instanceof handles nulls
                && contactId.equals(((DeleteContactCommand) other).contactId)); // state check
    }
}
