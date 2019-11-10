package seedu.address.logic.autocomplete.nodes.schedule;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.autocomplete.nodes.AutoCompleteNode;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Node} tracking {@code Schedule} {@code Tag} for autocompletion.
 */
public class ScheduleTagNode extends AutoCompleteNode<List<Schedule>> {

    public ScheduleTagNode(List<Schedule> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(schedule ->
                schedule.getTags().forEach(tag ->
                        values.add(tag.toString().replaceAll("\\[|\\]", ""))));
        return values;
    }
}
