package seedu.address.logic.nodes.schedule;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.Node;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Node} tracking {@code Schedule} {@code Tag} for autocompletion.
 */
public class ScheduleTagNode extends Node<Schedule> {

    public ScheduleTagNode(List<Schedule> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(schedule ->
                schedule.getTags().forEach(tag ->
                        values.add(tag.toString().replaceAll("\\[|\\]", ""))));
        return values;
    }
}
