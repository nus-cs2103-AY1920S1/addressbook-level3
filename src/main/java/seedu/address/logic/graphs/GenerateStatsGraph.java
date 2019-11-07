package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAT_TYPE;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.Edge;
import seedu.address.logic.GraphWithStartNode;
import seedu.address.logic.Node;
import seedu.address.logic.commands.statisticcommand.StatisticType;
import seedu.address.logic.nodes.schedule.ScheduleDateNode;
import seedu.address.logic.nodes.stats.StatsTypeNode;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

public class GenerateStatsGraph extends GraphWithStartNode {

    public GenerateStatsGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Schedule> scheduleList = model.getScheduleBook().getList();
        Node<?> statsStartNode = Node.emptyNode();
        setStartingNode(statsStartNode);
        Node<StatisticType> statsTypeNode = new StatsTypeNode(Arrays.asList(StatisticType.values()));
        Node<Schedule> statsDateNode = new ScheduleDateNode(scheduleList);
        edges.addAll(Arrays.asList(
                new Edge(PREFIX_STAT_TYPE, statsStartNode, statsTypeNode),
                new Edge(PREFIX_STARTING_DATE, statsTypeNode, statsDateNode),
                new Edge(PREFIX_ENDING_DATE, statsDateNode, statsDateNode)
        ));
    }

}
