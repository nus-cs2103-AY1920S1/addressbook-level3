package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.AutoCompleteEdge;
import seedu.address.logic.AutoCompleteNode;
import seedu.address.logic.GraphWithStartNodeAndPreamble;
import seedu.address.logic.nodes.EmptyAutoCompleteNode;
import seedu.address.logic.nodes.schedule.ScheduleDateNode;
import seedu.address.logic.nodes.schedule.ScheduleTagNode;
import seedu.address.logic.nodes.schedule.ScheduleTimeNode;
import seedu.address.logic.nodes.schedule.ScheduleVenueNode;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code AddScheduleCommand}.
 */
public class AddScheduleGraph extends GraphWithStartNodeAndPreamble {

    public AddScheduleGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Order> orderList = model.getFilteredOrderList();
        setDataList(orderList);
        List<Schedule> scheduleList = model.getScheduleBook().getList();
        AutoCompleteNode<?> addScheduleStartNode = EmptyAutoCompleteNode.getInstance();
        setStartingNode(addScheduleStartNode);
        ScheduleDateNode scheduleDateNode = new ScheduleDateNode(scheduleList);
        ScheduleTimeNode scheduleTimeNode = new ScheduleTimeNode(scheduleList);
        ScheduleVenueNode scheduleVenueNode = new ScheduleVenueNode(scheduleList);
        ScheduleTagNode scheduleTagNode = new ScheduleTagNode(scheduleList);
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_DATE, addScheduleStartNode, scheduleDateNode),
                new AutoCompleteEdge<>(PREFIX_TIME, scheduleDateNode, scheduleTimeNode),
                new AutoCompleteEdge<>(PREFIX_VENUE, scheduleTimeNode, scheduleVenueNode),
                new AutoCompleteEdge<>(PREFIX_TAG, scheduleVenueNode, scheduleTagNode),
                new AutoCompleteEdge<>(PREFIX_TAG, scheduleTagNode, scheduleTagNode)
        ));
    }

}
