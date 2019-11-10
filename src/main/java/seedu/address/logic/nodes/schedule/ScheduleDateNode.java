package seedu.address.logic.nodes.schedule;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.AutoCompleteNode;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Node} tracking {@code Schedule} {@code Date} for autocompletion.
 */
public class ScheduleDateNode extends AutoCompleteNode<List<Schedule>> {

    public ScheduleDateNode(List<Schedule> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        Optional<Calendar> min = pointer.stream()
                .map(Schedule::getCalendar)
                .min(Calendar::compareTo);
        Optional<Calendar> max = pointer.stream()
                .map(Schedule::getCalendar)
                .max(Calendar::compareTo);
        min.ifPresent(calendar -> values.add(StringUtil.convertCalendarDateToString(calendar)));
        max.ifPresent(calendar -> values.add(StringUtil.convertCalendarDateToString(calendar)));
        return values;
    }

}
