package seedu.address.logic.events.schedule;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AutoScheduleCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.system.SetItineraryCommand;
import seedu.address.logic.events.Event;
import seedu.address.model.Itinerary;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyItinerary;
import seedu.address.model.field.Address;

/**
 * An event representing an 'autoschedule' command.
 */
public class AutoScheduleEvent implements Event {
    private final ReadOnlyItinerary previousItinerary;
    private final List<Object> draftSchedule;
    private final Address address;
    private final List<Index> days;

    public AutoScheduleEvent(List<Object> draftSchedule, Address address, List<Index> days, Model model) {
        this.draftSchedule = draftSchedule;
        this.address = address;
        this.days = days;
        this.previousItinerary = new Itinerary(model.getItinerary());
    }

    public UndoableCommand undo() {
        return new SetItineraryCommand(previousItinerary);
    }

    public UndoableCommand redo() {
        return new AutoScheduleCommand(draftSchedule, address, days);
    }
}
