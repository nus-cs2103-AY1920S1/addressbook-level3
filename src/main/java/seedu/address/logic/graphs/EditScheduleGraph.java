package seedu.address.logic.graphs;

import seedu.address.logic.Edge;
import seedu.address.logic.GraphWithStartNodeAndPreamble;
import seedu.address.logic.Node;
import seedu.address.logic.nodes.schedule.ScheduleDateNode;
import seedu.address.logic.nodes.schedule.ScheduleTagNode;
import seedu.address.logic.nodes.schedule.ScheduleTimeNode;
import seedu.address.logic.nodes.schedule.ScheduleVenueNode;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

public class EditScheduleGraph extends GraphWithStartNodeAndPreamble {

    public EditScheduleGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Schedule> scheduleList = model.getFilteredScheduleList();
        setDataList(scheduleList);
        Node<?> editScheduleStartNode = Node.emptyNode();
        setStartingNode(editScheduleStartNode);
        Node<Schedule> scheduleDateNode = new ScheduleDateNode(scheduleList);
        Node<Schedule> scheduleVenueNode = new ScheduleVenueNode(scheduleList);
        Node<Schedule> scheduleTimeNode = new ScheduleTimeNode(scheduleList);
        Node<Schedule> scheduleTagNode = new ScheduleTagNode(scheduleList);
        edges.addAll(Arrays.asList(
                new Edge(PREFIX_DATE, editScheduleStartNode, scheduleDateNode),
                new Edge(PREFIX_TIME, editScheduleStartNode, scheduleTimeNode),
                new Edge(PREFIX_VENUE, editScheduleStartNode, scheduleVenueNode),
                new Edge(PREFIX_TAG, editScheduleStartNode, scheduleTagNode),
                new Edge(PREFIX_DATE, scheduleDateNode, scheduleDateNode),
                new Edge(PREFIX_TIME, scheduleDateNode, scheduleTimeNode),
                new Edge(PREFIX_VENUE, scheduleDateNode, scheduleVenueNode),
                new Edge(PREFIX_TAG, scheduleDateNode, scheduleTagNode),
                new Edge(PREFIX_DATE, scheduleTimeNode, scheduleDateNode),
                new Edge(PREFIX_TIME, scheduleTimeNode, scheduleTimeNode),
                new Edge(PREFIX_VENUE, scheduleTimeNode, scheduleVenueNode),
                new Edge(PREFIX_TAG, scheduleTimeNode, scheduleTagNode),
                new Edge(PREFIX_DATE, scheduleVenueNode, scheduleDateNode),
                new Edge(PREFIX_TIME, scheduleVenueNode, scheduleTimeNode),
                new Edge(PREFIX_VENUE, scheduleVenueNode, scheduleVenueNode),
                new Edge(PREFIX_TAG, scheduleVenueNode, scheduleTagNode),
                new Edge(PREFIX_DATE, scheduleTagNode, scheduleDateNode),
                new Edge(PREFIX_TIME, scheduleTagNode, scheduleTimeNode),
                new Edge(PREFIX_VENUE, scheduleTagNode, scheduleVenueNode),
                new Edge(PREFIX_TAG, scheduleTagNode, scheduleTagNode)
        ));
    }

}
