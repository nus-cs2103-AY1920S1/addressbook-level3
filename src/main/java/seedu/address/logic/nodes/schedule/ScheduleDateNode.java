package seedu.address.logic.nodes.schedule;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Node;
import seedu.address.model.schedule.Schedule;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

public class ScheduleDateNode extends Node<Schedule> {

    public ScheduleDateNode(List<Schedule> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        Optional<Calendar> min = backingList.stream()
                .map(Schedule::getCalendar)
                .min(Calendar::compareTo);
        Optional<Calendar> max = backingList.stream()
                .map(Schedule::getCalendar)
                .max(Calendar::compareTo);
        min.ifPresent(calendar -> values.add(StringUtil.convertCalendarDateToString(calendar)));
        max.ifPresent(calendar -> values.add(StringUtil.convertCalendarDateToString(calendar)));
        return values;
    }

}
