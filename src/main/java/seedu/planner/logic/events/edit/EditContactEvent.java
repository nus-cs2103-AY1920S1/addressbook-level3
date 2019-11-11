package seedu.planner.logic.events.edit;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
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
    private final Logger logger = LogsCenter.getLogger(EditContactEvent.class);

    public EditContactEvent(Index index, EditContactDescriptor editInfo, Contact oldContact) {
        this.index = index;
        this.editInfo = editInfo;
        this.oldContact = oldContact;
    }

    /**
     * A method to undo the effects of an EditContactCommand.
     * @return returns EditContactCommand with the old Contact previously in the model.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new EditContactCommand(index, null, oldContact);
    }

    /**
     * A method to undo the effects of an EditContactCommand.
     * @return returns EditContactCommand with the initial user input parameters.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new EditContactCommand(index, editInfo, true);
    }

    @Override
    public String toString() {
        return "EDIT CONTACT EVENT";
    }
}
