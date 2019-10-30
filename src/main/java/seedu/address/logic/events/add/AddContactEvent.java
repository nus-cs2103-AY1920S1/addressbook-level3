package seedu.address.logic.events.add;

import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.events.Event;
import seedu.address.model.contact.Contact;

/**
 * An event representing an 'add contact' command
 */
public class AddContactEvent implements Event {
    private final Contact contactAdded;

    public AddContactEvent(Contact contactAdded) {
        this.contactAdded = contactAdded;
    }

    public UndoableCommand undo() {
        return new DeleteContactCommand(contactAdded);
    }

    public UndoableCommand redo() {
        return new AddContactCommand(contactAdded);
    }
}
