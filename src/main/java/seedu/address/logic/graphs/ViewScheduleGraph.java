package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.Edge;
import seedu.address.logic.GraphWithStartNode;
import seedu.address.logic.Node;
import seedu.address.logic.nodes.schedule.ScheduleDateNode;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

public class ViewScheduleGraph extends GraphWithStartNode {

    public ViewScheduleGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Schedule> scheduleList = model.getFilteredScheduleList();
        Node<?> viewScheduleStartNode = Node.emptyNode();
        setStartingNode(viewScheduleStartNode);
        Node<Schedule> scheduleDateNode = new ScheduleDateNode(scheduleList);
        edges.addAll(
                new Edge(PREFIX_DATE ,viewScheduleStartNode, scheduleDateNode)
        );
    }

}
