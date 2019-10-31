package seedu.address.logic.events.clear;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.system.UndoClearCommand;
import seedu.address.logic.events.Event;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAccommodation;
import seedu.address.model.ReadOnlyActivity;
import seedu.address.model.ReadOnlyContact;
import seedu.address.model.ReadOnlyItinerary;

/**
 * An event representing a 'clear' command.
 */
public class ClearEvent implements Event {
    private final Model prevModelManager;

    public ClearEvent(Model model) {
        prevModelManager = new ModelManager(model.getAccommodations(), model.getActivities(), model.getContacts(),
                model.getItinerary(), model.getUserPrefs());
    }

    /**
     * A undo method to undo the effects of a Clear command previously called.
     * @return A UndoClearCommand to undo the effects of the recent Clear command.
     */
    public UndoableCommand undo() {
        ReadOnlyAccommodation prevAccommodation = prevModelManager.getAccommodations();
        ReadOnlyActivity prevActivity = prevModelManager.getActivities();
        ReadOnlyContact prevContact = prevModelManager.getContacts();
        ReadOnlyItinerary prevItinerary = prevModelManager.getItinerary();
        return new UndoClearCommand(prevAccommodation, prevActivity, prevContact, prevItinerary);
    }

    public UndoableCommand redo() {
        return new ClearCommand();
    }
}
