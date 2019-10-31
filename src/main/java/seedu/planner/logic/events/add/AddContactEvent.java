package seedu.planner.logic.events.add;

import seedu.planner.logic.commands.AddContactCommand;
import seedu.planner.logic.commands.DeleteContactCommand;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.contact.Contact;

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
