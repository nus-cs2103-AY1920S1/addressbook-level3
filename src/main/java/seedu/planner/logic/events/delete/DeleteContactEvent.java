package seedu.planner.logic.events.delete;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.addcommand.AddContactCommand;
import seedu.planner.logic.commands.deletecommand.DeleteContactCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.contact.Contact;
//@@author OneArmyj
/**
 * An event representing a 'delete contact' command.
 */
public class DeleteContactEvent implements Event {
    private final Index index;
    private final Contact deletedContact;

    public DeleteContactEvent(Index index, Contact deletedContact) {
        this.index = index;
        this.deletedContact = deletedContact;
    }

    public UndoableCommand undo() {
        return new AddContactCommand(index, deletedContact);
    }

    public UndoableCommand redo() {
        return new DeleteContactCommand(index, true);
    }
}

