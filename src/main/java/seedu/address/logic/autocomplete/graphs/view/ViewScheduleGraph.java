package seedu.address.logic.autocomplete.graphs.view;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;

import seedu.address.logic.autocomplete.graphs.Edge;
import seedu.address.logic.autocomplete.graphs.GraphWithStartNode;
import seedu.address.logic.autocomplete.nodes.schedule.ScheduleDateNode;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code ScheduleCommand}.
 */
public class ViewScheduleGraph extends GraphWithStartNode {

    public ViewScheduleGraph(Model model) {
        super();
        initialise(model);
    }

    private void initialise(Model model) {
        List<Schedule> scheduleList = model.getFilteredScheduleList();
        ScheduleDateNode scheduleDateNode = new ScheduleDateNode(scheduleList);
        addEdges(
                new Edge<>(PREFIX_DATE, startingNode, scheduleDateNode)
        );
    }

}
