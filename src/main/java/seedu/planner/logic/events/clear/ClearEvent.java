package seedu.planner.logic.events.clear;

import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.system.UndoClearCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.ReadOnlyAccommodation;
import seedu.planner.model.ReadOnlyActivity;
import seedu.planner.model.ReadOnlyContact;
import seedu.planner.model.ReadOnlyItinerary;
//@@author OneArmyj
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
        return new ClearCommand(true);
    }
}
