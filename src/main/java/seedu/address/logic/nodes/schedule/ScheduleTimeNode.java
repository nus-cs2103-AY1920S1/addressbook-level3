package seedu.address.logic.nodes.schedule;

import java.util.Calendar;
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
        backingList.forEach(schedule -> {
            Calendar calendar = schedule.getCalendar();
            String hour = String.valueOf(calendar.get(Calendar.HOUR));
            String minute = String.valueOf(calendar.get(Calendar.MINUTE));
            String display = String.format("%s.%s", hour, minute);
            values.add(display);
        });
        return values;
    }

}
