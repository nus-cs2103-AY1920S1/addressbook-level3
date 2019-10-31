package seedu.planner.logic.events.delete;

import java.util.List;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.AddContactCommand;
import seedu.planner.logic.commands.DeleteContactCommand;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Model;
import seedu.planner.model.contact.Contact;

/**
 * An event representing a 'delete contact' command.
 */
public class DeleteContactEvent implements Event {
    private final Index index;
    private final Contact deletedContact;

    public DeleteContactEvent(Index index, Model model) {
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
    private Contact generateDeletedContact(Model model) {
        List<Contact> lastShownList = model.getFilteredContactList();
        assert(index.getZeroBased() < lastShownList.size());
        Contact contactToDelete = lastShownList.get(index.getZeroBased());
        return contactToDelete;
    }
}

