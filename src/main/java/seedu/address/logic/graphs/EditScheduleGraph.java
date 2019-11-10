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
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code EditScheduleCommand}.
 */
public class EditScheduleGraph extends GraphWithStartNodeAndPreamble {

    public EditScheduleGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Schedule> scheduleList = model.getFilteredScheduleList();
        setDataList(scheduleList);
        AutoCompleteNode<?> editScheduleStartNode = EmptyAutoCompleteNode.getInstance();
        setStartingNode(editScheduleStartNode);
        ScheduleDateNode scheduleDateNode = new ScheduleDateNode(scheduleList);
        ScheduleVenueNode scheduleVenueNode = new ScheduleVenueNode(scheduleList);
        ScheduleTimeNode scheduleTimeNode = new ScheduleTimeNode(scheduleList);
        ScheduleTagNode scheduleTagNode = new ScheduleTagNode(scheduleList);
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_DATE, editScheduleStartNode, scheduleDateNode),
                new AutoCompleteEdge<>(PREFIX_TIME, editScheduleStartNode, scheduleTimeNode),
                new AutoCompleteEdge<>(PREFIX_VENUE, editScheduleStartNode, scheduleVenueNode),
                new AutoCompleteEdge<>(PREFIX_TAG, editScheduleStartNode, scheduleTagNode),
                new AutoCompleteEdge<>(PREFIX_DATE, scheduleDateNode, scheduleDateNode),
                new AutoCompleteEdge<>(PREFIX_TIME, scheduleDateNode, scheduleTimeNode),
                new AutoCompleteEdge<>(PREFIX_VENUE, scheduleDateNode, scheduleVenueNode),
                new AutoCompleteEdge<>(PREFIX_TAG, scheduleDateNode, scheduleTagNode),
                new AutoCompleteEdge<>(PREFIX_DATE, scheduleTimeNode, scheduleDateNode),
                new AutoCompleteEdge<>(PREFIX_TIME, scheduleTimeNode, scheduleTimeNode),
                new AutoCompleteEdge<>(PREFIX_VENUE, scheduleTimeNode, scheduleVenueNode),
                new AutoCompleteEdge<>(PREFIX_TAG, scheduleTimeNode, scheduleTagNode),
                new AutoCompleteEdge<>(PREFIX_DATE, scheduleVenueNode, scheduleDateNode),
                new AutoCompleteEdge<>(PREFIX_TIME, scheduleVenueNode, scheduleTimeNode),
                new AutoCompleteEdge<>(PREFIX_VENUE, scheduleVenueNode, scheduleVenueNode),
                new AutoCompleteEdge<>(PREFIX_TAG, scheduleVenueNode, scheduleTagNode),
                new AutoCompleteEdge<>(PREFIX_DATE, scheduleTagNode, scheduleDateNode),
                new AutoCompleteEdge<>(PREFIX_TIME, scheduleTagNode, scheduleTimeNode),
                new AutoCompleteEdge<>(PREFIX_VENUE, scheduleTagNode, scheduleVenueNode),
                new AutoCompleteEdge<>(PREFIX_TAG, scheduleTagNode, scheduleTagNode)
        ));
    }

}
