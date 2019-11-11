package seedu.address.logic.autocomplete.graphs.edit;

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
 * Represents a {@code Graph} used to support autocomplete for {@code EditScheduleCommand}.
 */
public class EditScheduleGraph extends GraphWithStartNodeAndPreamble {

    public EditScheduleGraph(Model model) {
        super(model.getFilteredScheduleList());
        initialise(model);
    }

    /**
     * Initialises this graph's {@code Node}s.
     */
    private void initialise(Model model) {
        List<Schedule> scheduleList = model.getFilteredScheduleList();
        ScheduleDateNode scheduleDateNode = new ScheduleDateNode(scheduleList);
        ScheduleVenueNode scheduleVenueNode = new ScheduleVenueNode(scheduleList);
        ScheduleTimeNode scheduleTimeNode = new ScheduleTimeNode(scheduleList);
        ScheduleTagNode scheduleTagNode = new ScheduleTagNode(scheduleList);
        addEdges(
                new Edge<>(PREFIX_DATE, startingNode, scheduleDateNode),
                new Edge<>(PREFIX_TIME, startingNode, scheduleTimeNode),
                new Edge<>(PREFIX_VENUE, startingNode, scheduleVenueNode),
                new Edge<>(PREFIX_TAG, startingNode, scheduleTagNode),
                new Edge<>(PREFIX_DATE, scheduleDateNode, scheduleDateNode),
                new Edge<>(PREFIX_TIME, scheduleDateNode, scheduleTimeNode),
                new Edge<>(PREFIX_VENUE, scheduleDateNode, scheduleVenueNode),
                new Edge<>(PREFIX_TAG, scheduleDateNode, scheduleTagNode),
                new Edge<>(PREFIX_DATE, scheduleTimeNode, scheduleDateNode),
                new Edge<>(PREFIX_TIME, scheduleTimeNode, scheduleTimeNode),
                new Edge<>(PREFIX_VENUE, scheduleTimeNode, scheduleVenueNode),
                new Edge<>(PREFIX_TAG, scheduleTimeNode, scheduleTagNode),
                new Edge<>(PREFIX_DATE, scheduleVenueNode, scheduleDateNode),
                new Edge<>(PREFIX_TIME, scheduleVenueNode, scheduleTimeNode),
                new Edge<>(PREFIX_VENUE, scheduleVenueNode, scheduleVenueNode),
                new Edge<>(PREFIX_TAG, scheduleVenueNode, scheduleTagNode),
                new Edge<>(PREFIX_DATE, scheduleTagNode, scheduleDateNode),
                new Edge<>(PREFIX_TIME, scheduleTagNode, scheduleTimeNode),
                new Edge<>(PREFIX_VENUE, scheduleTagNode, scheduleVenueNode),
                new Edge<>(PREFIX_TAG, scheduleTagNode, scheduleTagNode)
        );
    }

}
