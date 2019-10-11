package tagline.logic.commands.contact;

import static java.util.Objects.requireNonNull;

import java.util.List;

import tagline.commons.core.Messages;
import tagline.commons.core.index.Index;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.contact.Contact;

/**
 * Deletes a contact identified using it's displayed index from the address book.
 */
public class DeleteContactCommand extends ContactCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_KEY + " " + COMMAND_WORD
            + ": Deletes the contact identified by the index number used in the displayed contact list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";

    private final Index targetIndex;

    public DeleteContactCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteContact(contactToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteContactCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteContactCommand) other).targetIndex)); // state check
    }
}
