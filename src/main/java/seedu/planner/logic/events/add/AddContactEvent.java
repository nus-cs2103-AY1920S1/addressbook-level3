package seedu.planner.logic.events.add;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.addcommand.AddContactCommand;
import seedu.planner.logic.commands.deletecommand.DeleteContactCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.contact.Contact;
//@@author OneArmyj
/**
 * An event representing an 'add contact' command
 */
public class AddContactEvent implements Event {
    private final Contact contactAdded;
    private final Logger logger = LogsCenter.getLogger(AddContactEvent.class);

    public AddContactEvent(Contact contactAdded) {
        this.contactAdded = contactAdded;
    }

    /**
     * A method to undo the effects of an AddContactCommand.
     * @return returns DeleteContactCommand.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new DeleteContactCommand(null, contactAdded);
    }

    /**
     * A method to redo the effects of an AddContactCommand.
     * @return returns AddContactCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new AddContactCommand(contactAdded, true);
    }

    @Override
    public String toString() {
        return "ADD CONTACT EVENT";
    }
}
