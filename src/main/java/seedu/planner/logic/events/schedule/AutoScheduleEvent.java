package seedu.planner.logic.events.schedule;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.schedulecommand.AutoScheduleCommand;
import seedu.planner.logic.commands.system.SetItineraryCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Itinerary;
import seedu.planner.model.Model;
import seedu.planner.model.ReadOnlyItinerary;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.NameOrTagWithTime;
//@@author OneArmyj
/**
 * An event representing an 'autoschedule' command.
 */
public class AutoScheduleEvent implements Event {
    private final ReadOnlyItinerary previousItinerary;
    private final List<NameOrTagWithTime> draftSchedule;
    private final Optional<Address> address;
    private final List<Index> days;
    private final Logger logger = LogsCenter.getLogger(AutoScheduleEvent.class);

    public AutoScheduleEvent(List<NameOrTagWithTime> draftSchedule, Optional<Address> address,
                             List<Index> days, Model model) {
        this.draftSchedule = draftSchedule;
        this.address = address;
        this.days = days;
        this.previousItinerary = new Itinerary(model.getItinerary());
    }

    /**
     * A method to undo the effects of AutoScheduleCommand.
     * @return returns SetItineraryCommand to restore the previous itinerary in the model.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new SetItineraryCommand(previousItinerary);
    }

    /**
     * A method to redo the effects of AutoScheduleCommand
     * @return returns AutoScheduleCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new AutoScheduleCommand(draftSchedule, address, days, true);
    }

    @Override
    public String toString() {
        return "AUTOSCHEDULE EVENT";
    }
}
