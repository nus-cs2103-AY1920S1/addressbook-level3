package seedu.planner.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.contact.Contact;

/**
 * Removes a contact from the contact list.
 */
public class DeleteContactCommand extends DeleteCommand {

    public static final String SECOND_COMMAND_WORD = "contact";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Deletes the contact identified by the index "
                    + "number used in the displayed contact list.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " INDEX(must be a positive integer)",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 1"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "<INDEX>"
    );

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";

    private final Index targetIndex;
    private final Contact toDelete;
    private final boolean isUndoRedo;

    public DeleteContactCommand(Index targetIndex, boolean isUndoRedo) {
        toDelete = null;
        this.targetIndex = targetIndex;
        this.isUndoRedo = isUndoRedo;
    }

    // Constructor used to undo AddContactEvent and DeleteContactEvent
    public DeleteContactCommand(Index targetIndex, Contact contact) {
        requireNonNull(contact);
        toDelete = contact;
        this.targetIndex = targetIndex;
        this.isUndoRedo = true;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    public Contact getToDelete() {
        return toDelete;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();
        Contact contactToDelete;

        if (toDelete != null) {
            contactToDelete = toDelete;
        } else if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        } else {
            contactToDelete = lastShownList.get(targetIndex.getZeroBased());
        }
        Index indexOfContact = findIndexOfContact(model, contactToDelete);

        if (toDelete == null && !isUndoRedo) {
            //Not due to undo method of AddContactEvent or redo method of DeleteContactEvent
            DeleteContactCommand newCommand = new DeleteContactCommand(indexOfContact, contactToDelete);
            updateEventStack(newCommand, model);
        }
        model.deleteContact(contactToDelete);

        return new CommandResult(
            String.format(MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete),
            new ResultInformation[] {
                new ResultInformation(
                        contactToDelete,
                        indexOfContact,
                        String.format(MESSAGE_DELETE_CONTACT_SUCCESS, "")
                )
            },
            new UiFocus[] { UiFocus.CONTACT, UiFocus.INFO }
        );
    }

    /**
     * Returns the index of contact in the model.
     * Precondition: the {@code contact} must have not been deleted before this.
     */
    private Index findIndexOfContact(Model model, Contact contact) {
        Optional<Index> indexOfContact = model.getContactIndex(contact);
        if (indexOfContact.isEmpty()) {
            throw new AssertionError("Contact should not have been deleted before this.");
        }
        return indexOfContact.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteContactCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteContactCommand) other).targetIndex)); // state check
    }
}
