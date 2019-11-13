package seedu.planner.logic.events.delete;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
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
    private final Logger logger = LogsCenter.getLogger(DeleteContactEvent.class);

    public DeleteContactEvent(Index index, Contact deletedContact) {
        this.index = index;
        this.deletedContact = deletedContact;
    }

    /**
     * A method to undo the effects of an DeleteContactCommand.
     * @return returns AddContactCommand.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new AddContactCommand(index, deletedContact);
    }

    /**
     * A method to redo the effects of an DeleteContactCommand.
     * @return returns DeleteContactCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new DeleteContactCommand(index, true);
    }

    @Override
    public String toString() {
        return "DELETE CONTACT EVENT";
    }
}

