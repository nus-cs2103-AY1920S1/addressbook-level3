package seedu.planner.logic.events.clear;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
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
    private final Logger logger = LogsCenter.getLogger(ClearEvent.class);

    public ClearEvent(Model model) {
        prevModelManager = new ModelManager(model.getAccommodations(), model.getActivities(), model.getContacts(),
                model.getItinerary(), model.getUserPrefs());
    }

    /**
     * A method to undo the effects of a ClearCommand.
     * @return returns UndoClearCommand.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        ReadOnlyAccommodation prevAccommodation = prevModelManager.getAccommodations();
        ReadOnlyActivity prevActivity = prevModelManager.getActivities();
        ReadOnlyContact prevContact = prevModelManager.getContacts();
        ReadOnlyItinerary prevItinerary = prevModelManager.getItinerary();
        return new UndoClearCommand(prevAccommodation, prevActivity, prevContact, prevItinerary);
    }

    /**
     * A method to redo the effects of a ClearCommand.
     * @return returns ClearCommand.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new ClearCommand(true);
    }

    @Override
    public String toString() {
        return "CLEAR EVENT";
    }
}
