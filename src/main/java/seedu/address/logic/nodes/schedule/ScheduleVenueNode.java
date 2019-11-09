package seedu.address.logic.nodes.schedule;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.AutoCompleteNode;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Node} tracking {@code Schedule} {@code Venue} for autocompletion.
 */
public class ScheduleVenueNode extends AutoCompleteNode<List<Schedule>> {

    public ScheduleVenueNode(List<Schedule> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(schedule -> values.add(schedule.getVenue().toString()));
        return values;
    }

}
