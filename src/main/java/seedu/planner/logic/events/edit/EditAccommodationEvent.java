package seedu.planner.logic.events.edit;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.editcommand.EditAccommodationCommand;
import seedu.planner.logic.commands.editcommand.EditAccommodationCommand.EditAccommodationDescriptor;
import seedu.planner.logic.events.Event;
import seedu.planner.model.accommodation.Accommodation;
//@@author OneArmyj
/**
 * An event representing a 'edit accommodation' command.
 */
public class EditAccommodationEvent implements Event {
    private final Index index;
    private final EditAccommodationDescriptor editInfo;
    private final Accommodation oldAccommodation;
    private final Logger logger = LogsCenter.getLogger(EditAccommodationEvent.class);

    public EditAccommodationEvent(Index index, EditAccommodationDescriptor editInfo, Accommodation oldAccommodation) {
        this.index = index;
        this.editInfo = editInfo;
        this.oldAccommodation = oldAccommodation;
    }

    /**
     * A method to undo the effects of an EditAccommodationCommand.
     * @return returns EditAccommodationCommand with the old Accommodation previously in the model.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new EditAccommodationCommand(index, null, oldAccommodation);
    }

    /**
     * A method to undo the effects of an EditAccommodationCommand.
     * @return returns EditAccommodationCommand with the initial user input parameters.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new EditAccommodationCommand(index, editInfo, true);
    }

    @Override
    public String toString() {
        return "EDIT ACCOMMODATION EVENT";
    }
}
