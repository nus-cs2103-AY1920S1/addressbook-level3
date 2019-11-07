package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.Edge;
import seedu.address.logic.GraphWithStartNode;
import seedu.address.logic.Node;
import seedu.address.logic.nodes.schedule.ScheduleDateNode;
import seedu.address.logic.nodes.schedule.ScheduleTagNode;
import seedu.address.logic.nodes.schedule.ScheduleTimeNode;
import seedu.address.logic.nodes.schedule.ScheduleVenueNode;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code AddScheduleCommand}.
 */
public class AddScheduleGraph extends GraphWithStartNode {

    public AddScheduleGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Schedule> scheduleList = model.getScheduleBook().getList();
        Node<?> addScheduleStartNode = Node.emptyNode();
        setStartingNode(addScheduleStartNode);
        Node<Schedule> scheduleDateNode = new ScheduleDateNode(scheduleList);
        Node<Schedule> scheduleTimeNode = new ScheduleTimeNode(scheduleList);
        Node<Schedule> scheduleVenueNode = new ScheduleVenueNode(scheduleList);
        Node<Schedule> scheduleTagNode = new ScheduleTagNode(scheduleList);
        edges.addAll(Arrays.asList(
                new Edge(PREFIX_DATE, addScheduleStartNode, scheduleDateNode),
                new Edge(PREFIX_TIME, scheduleDateNode, scheduleTimeNode),
                new Edge(PREFIX_VENUE, scheduleTimeNode, scheduleVenueNode),
                new Edge(PREFIX_TAG, scheduleVenueNode, scheduleTagNode),
                new Edge(PREFIX_TAG, scheduleTagNode, scheduleTagNode)
        ));
    }

}
