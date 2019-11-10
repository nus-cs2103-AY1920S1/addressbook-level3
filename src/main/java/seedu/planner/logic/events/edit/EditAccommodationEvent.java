package seedu.planner.logic.events.edit;

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

    public EditAccommodationEvent(Index index, EditAccommodationDescriptor editInfo, Accommodation oldAccommodation) {
        this.index = index;
        this.editInfo = editInfo;
        this.oldAccommodation = oldAccommodation;
    }

    public UndoableCommand undo() {
        return new EditAccommodationCommand(index, null, oldAccommodation);
    }

    public UndoableCommand redo() {
        return new EditAccommodationCommand(index, editInfo, true);
    }

}
