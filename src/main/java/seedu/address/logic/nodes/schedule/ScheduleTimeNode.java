package seedu.address.logic.nodes.schedule;

import java.util.Calendar;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.AutoCompleteNode;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Node} tracking {@code Schedule} time for autocompletion.
 */
public class ScheduleTimeNode extends AutoCompleteNode<List<Schedule>> {

    public ScheduleTimeNode(List<Schedule> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(schedule -> {
            Calendar calendar = schedule.getCalendar();
            String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
            String minute = String.valueOf(calendar.get(Calendar.MINUTE));
            String display = String.format("%s.%s", hour, minute);
            values.add(display);
        });
        return values;
    }

}
