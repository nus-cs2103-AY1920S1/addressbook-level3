package seedu.planner.logic.events.edit;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.editcommand.EditContactCommand;
import seedu.planner.logic.commands.editcommand.EditContactCommand.EditContactDescriptor;
import seedu.planner.logic.events.Event;
import seedu.planner.model.contact.Contact;
//@@author OneArmyj
/**
 * An event representing a 'edit contact' command.
 */
public class EditContactEvent implements Event {
    private final Index index;
    private final EditContactDescriptor editInfo;
    private final Contact oldContact;

    public EditContactEvent(Index index, EditContactDescriptor editInfo, Contact oldContact) {
        this.index = index;
        this.editInfo = editInfo;
        this.oldContact = oldContact;
    }

    public UndoableCommand undo() {
        return new EditContactCommand(index, null, oldContact);
    }

    public UndoableCommand redo() {
        return new EditContactCommand(index, editInfo, true);
    }

}
