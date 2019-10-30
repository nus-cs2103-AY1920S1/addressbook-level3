package seedu.address.logic.events.add;

import seedu.address.logic.commands.AddAccommodationCommand;
import seedu.address.logic.commands.DeleteAccommodationCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.events.Event;
import seedu.address.model.itineraryitem.accommodation.Accommodation;

/**
 * An event representing an 'add accommodation' command.
 */
public class AddAccommodationEvent implements Event {
    private final Accommodation accommodationAdded;

    public AddAccommodationEvent(Accommodation accommodationAdded) {
        this.accommodationAdded = accommodationAdded;
    }

    public UndoableCommand undo() {
        return new DeleteAccommodationCommand(accommodationAdded);
    }

    public UndoableCommand redo() {
        return new AddAccommodationCommand(accommodationAdded);
    }
}
