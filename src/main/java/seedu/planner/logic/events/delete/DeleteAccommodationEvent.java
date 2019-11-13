package seedu.planner.logic.events.delete;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.addcommand.AddAccommodationCommand;
import seedu.planner.logic.commands.deletecommand.DeleteAccommodationCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.accommodation.Accommodation;
//@@author OneArmyj
/**
 * An event representing a 'delete accommodation' command.
 */
public class DeleteAccommodationEvent implements Event {
    private final Index index;
    private final Accommodation deletedAccommodation;
    private final Logger logger = LogsCenter.getLogger(DeleteAccommodationEvent.class);

    public DeleteAccommodationEvent(Index index, Accommodation deletedAccommodation) {
        this.index = index;
        this.deletedAccommodation = deletedAccommodation;
    }

    /**
     * A method to undo the effects of an DeleteAccommodationCommand.
     * @return returns AddAccommodationCommand.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new AddAccommodationCommand(index, deletedAccommodation);
    }

    /**
     * A method to redo the effects of an DeleteAccommodationCommand.
     * @return returns DeleteAccommodationCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new DeleteAccommodationCommand(index, true);
    }

    @Override
    public String toString() {
        return "DELETE ACCOMMODATION EVENT";
    }
}
