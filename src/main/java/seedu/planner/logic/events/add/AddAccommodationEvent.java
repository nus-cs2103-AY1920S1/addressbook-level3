package seedu.planner.logic.events.add;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.addcommand.AddAccommodationCommand;
import seedu.planner.logic.commands.deletecommand.DeleteAccommodationCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.accommodation.Accommodation;
//@@author OneArmyj
/**
 * An event representing an 'add accommodation' command.
 */
public class AddAccommodationEvent implements Event {
    private final Accommodation accommodationAdded;
    private final Logger logger = LogsCenter.getLogger(AddAccommodationEvent.class);

    public AddAccommodationEvent(Accommodation accommodationAdded) {
        this.accommodationAdded = accommodationAdded;
    }

    /**
     * A method to undo the effects of an AddAccommodationCommand.
     * @return returns DeleteAccommodationCommand.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new DeleteAccommodationCommand(null, accommodationAdded);
    }

    /**
     * A method to redo the effects of an AddAccommodationCommand.
     * @return returns AddAccommodationCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new AddAccommodationCommand(accommodationAdded, true);
    }

    @Override
    public String toString() {
        return "ADD ACCOMMODATION EVENT";
    }
}
