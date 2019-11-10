package seedu.planner.logic.events.add;

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

    public AddAccommodationEvent(Accommodation accommodationAdded) {
        this.accommodationAdded = accommodationAdded;
    }

    public UndoableCommand undo() {
        return new DeleteAccommodationCommand(null, accommodationAdded);
    }

    public UndoableCommand redo() {
        return new AddAccommodationCommand(accommodationAdded, true);
    }
}
