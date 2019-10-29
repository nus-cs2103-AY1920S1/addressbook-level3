package seedu.address.logic.nodes.stats;

import seedu.address.model.schedule.Schedule;
import seedu.address.logic.Node;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class StatsStartNode extends Node<Schedule> {

    public StatsStartNode(List<Schedule> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        return new TreeSet<>();
    }
}
