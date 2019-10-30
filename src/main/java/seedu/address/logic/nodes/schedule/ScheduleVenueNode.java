package seedu.address.logic.nodes.schedule;

import seedu.address.logic.Node;
import seedu.address.model.schedule.Schedule;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ScheduleVenueNode extends Node<Schedule> {

    public ScheduleVenueNode(List<Schedule> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(schedule -> values.add(schedule.getVenue().toString()));
        return values;
    }

}
