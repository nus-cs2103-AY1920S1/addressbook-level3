package seedu.address.logic.autocomplete.graphs.misc;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.autocomplete.edges.AutoCompleteEdge;
import seedu.address.logic.autocomplete.graphs.GraphWithStartNode;
import seedu.address.logic.autocomplete.nodes.AutoCompleteNode;
import seedu.address.logic.autocomplete.nodes.EmptyAutoCompleteNode;
import seedu.address.logic.autocomplete.nodes.schedule.ScheduleDateNode;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code ScheduleCommand}.
 */
public class ViewScheduleGraph extends GraphWithStartNode {

    public ViewScheduleGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Schedule> scheduleList = model.getFilteredScheduleList();
        AutoCompleteNode<?> viewScheduleStartNode = EmptyAutoCompleteNode.getInstance();
        setStartingNode(viewScheduleStartNode);
        ScheduleDateNode scheduleDateNode = new ScheduleDateNode(scheduleList);
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_DATE, viewScheduleStartNode, scheduleDateNode)
        ));
    }

}
