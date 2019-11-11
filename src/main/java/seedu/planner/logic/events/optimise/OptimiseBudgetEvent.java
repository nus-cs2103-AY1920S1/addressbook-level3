package seedu.planner.logic.events.optimise;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
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
    private final Logger logger = LogsCenter.getLogger(OptimiseBudgetEvent.class);

    public OptimiseBudgetEvent(Index index, Model model) {
        this.dayIndex = index;
        this.previousItinerary = new Itinerary(model.getItinerary());
    }

    /**
     * A method to undo the effects of OptimiseCommand.
     * @return returns SetItineraryCommand to restore the previous itinerary in the model.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new SetItineraryCommand(previousItinerary);
    }

    /**
     * A method to redo the effects of OptimiseCommand.
     * @return returns OptimiseCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new OptimiseCommand(dayIndex, true);
    }

    @Override
    public String toString() {
        return "OPTIMISE BUDGET EVENT";
    }
}
