package seedu.address.logic.graphs;

import seedu.address.logic.GraphWithPreamble;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

import java.util.List;

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
