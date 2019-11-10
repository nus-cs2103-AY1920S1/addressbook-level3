package seedu.planner.logic.events.delete;

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

    public DeleteAccommodationEvent(Index index, Accommodation deletedAccommodation) {
        this.index = index;
        this.deletedAccommodation = deletedAccommodation;
    }

    public UndoableCommand undo() {
        return new AddAccommodationCommand(index, deletedAccommodation);
    }

    public UndoableCommand redo() {
        return new DeleteAccommodationCommand(index, true);
    }

}
