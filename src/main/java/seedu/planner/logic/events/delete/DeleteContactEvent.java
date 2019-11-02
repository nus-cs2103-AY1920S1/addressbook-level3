package seedu.planner.logic.events.delete;

import java.util.List;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.AddContactCommand;
import seedu.planner.logic.commands.DeleteContactCommand;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.logic.events.exceptions.EventException;
import seedu.planner.model.Model;
import seedu.planner.model.contact.Contact;

/**
 * An event representing a 'delete contact' command.
 */
public class DeleteContactEvent implements Event {
    private final Index index;
    private final Contact deletedContact;

    public DeleteContactEvent(Index index, Model model) throws EventException {
        this.index = index;
        this.deletedContact = generateDeletedContact(model);
    }

    public UndoableCommand undo() {
        return new AddContactCommand(index, deletedContact);
    }

    public UndoableCommand redo() {
        return new DeleteContactCommand(index);
    }

    /**
     * A method to retrieve the Contact object that is going to be deleted.
     * @param model Current model in the application.
     * @return Contact to be deleted.
     */
    private Contact generateDeletedContact(Model model) throws EventException {
        List<Contact> lastShownList = model.getFilteredContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new EventException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToDelete = lastShownList.get(index.getZeroBased());
        return contactToDelete;
    }
}

