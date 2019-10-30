package seedu.address.logic.nodes.schedule;

import seedu.address.logic.Node;
import seedu.address.model.schedule.Schedule;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class AddScheduleStartNode extends Node<Schedule> {

    public AddScheduleStartNode(List<Schedule> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        return new TreeSet<>();
    }
}
