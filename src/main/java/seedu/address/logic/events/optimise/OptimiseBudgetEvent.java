package seedu.address.logic.events.optimise;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.OptimiseBudgetCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.system.SetItineraryCommand;
import seedu.address.logic.events.Event;
import seedu.address.model.Itinerary;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyItinerary;

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
        return new OptimiseBudgetCommand(dayIndex);
    }
}
