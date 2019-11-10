package seedu.planner.logic.events.optimise;

import seedu.planner.commons.core.index.Index;

import seedu.planner.logic.commands.OptimiseCommand;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.system.SetItineraryCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Itinerary;
import seedu.planner.model.Model;
import seedu.planner.model.ReadOnlyItinerary;
//@@author OneArmyj
/**
 * An event representing an 'optimise' command.
 */
public class OptimiseBudgetEvent implements Event {
    private final ReadOnlyItinerary previousItinerary;
    private final Index dayIndex;

    public OptimiseBudgetEvent(Index index, Model model) {
        this.dayIndex = index;
        this.previousItinerary = new Itinerary(model.getItinerary());
    }

    public UndoableCommand undo() {
        return new SetItineraryCommand(previousItinerary);
    }

    public UndoableCommand redo() {
        return new OptimiseCommand(dayIndex, true);
    }
}
