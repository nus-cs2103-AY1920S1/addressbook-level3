package seedu.address.logic.graphs;

import java.util.List;

import seedu.address.logic.GraphWithPreamble;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code DeleteScheduleCommand}.
 */
public class DeleteScheduleGraph extends GraphWithPreamble {

    public DeleteScheduleGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Schedule> scheduleList = model.getFilteredScheduleList();
        setDataList(scheduleList);
    }

}
