package seedu.address.logic.nodes.schedule;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.Node;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Node} tracking {@code Schedule} time for autocompletion.
 */
public class ScheduleTimeNode extends Node<Schedule> {

    public ScheduleTimeNode(List<Schedule> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(schedule -> values.add(schedule.getCalendar().getTime().toString()));
        return values;
    }
}
