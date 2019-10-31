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
import seedu.address.model.ReadOnlyUserPrefs;

public class ClearCommandEvent implements Event {
    private final Model prevModelManager;

    public ClearCommandEvent(Model model) {
        prevModelManager = new ModelManager(model.getAccommodations(), model.getActivities(), model.getContacts()
        , model.getItinerary(), model.getUserPrefs());
    }

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
