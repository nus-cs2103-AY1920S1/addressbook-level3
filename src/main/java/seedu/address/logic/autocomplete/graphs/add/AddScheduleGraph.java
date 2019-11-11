package seedu.address.logic.autocomplete.graphs.add;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.List;

import seedu.address.logic.autocomplete.graphs.Edge;
import seedu.address.logic.autocomplete.graphs.GraphWithStartNodeAndPreamble;
import seedu.address.logic.autocomplete.nodes.schedule.ScheduleDateNode;
import seedu.address.logic.autocomplete.nodes.schedule.ScheduleTagNode;
import seedu.address.logic.autocomplete.nodes.schedule.ScheduleTimeNode;
import seedu.address.logic.autocomplete.nodes.schedule.ScheduleVenueNode;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code AddScheduleCommand}.
 */
public class AddScheduleGraph extends GraphWithStartNodeAndPreamble {

    public AddScheduleGraph(Model model) {
        super(model.getFilteredOrderList());
        initialise(model);
    }

    private void initialise(Model model) {
        List<Schedule> scheduleList = model.getScheduleBook().getList();
        ScheduleDateNode scheduleDateNode = new ScheduleDateNode(scheduleList);
        ScheduleTimeNode scheduleTimeNode = new ScheduleTimeNode(scheduleList);
        ScheduleVenueNode scheduleVenueNode = new ScheduleVenueNode(scheduleList);
        ScheduleTagNode scheduleTagNode = new ScheduleTagNode(scheduleList);
        addEdges(
                new Edge<>(PREFIX_DATE, startingNode, scheduleDateNode),
                new Edge<>(PREFIX_TIME, scheduleDateNode, scheduleTimeNode),
                new Edge<>(PREFIX_VENUE, scheduleTimeNode, scheduleVenueNode),
                new Edge<>(PREFIX_TAG, scheduleVenueNode, scheduleTagNode),
                new Edge<>(PREFIX_TAG, scheduleTagNode, scheduleTagNode)
        );
    }

}
