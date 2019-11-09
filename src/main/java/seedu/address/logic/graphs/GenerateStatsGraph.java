package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAT_TYPE;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.AutoCompleteEdge;
import seedu.address.logic.AutoCompleteNode;
import seedu.address.logic.GraphWithStartNode;
import seedu.address.logic.commands.statisticcommand.StatisticType;
import seedu.address.logic.nodes.EmptyAutoCompleteNode;
import seedu.address.logic.nodes.schedule.ScheduleDateNode;
import seedu.address.logic.nodes.stats.StatsTypeNode;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code StatsCommand}.
 */
public class GenerateStatsGraph extends GraphWithStartNode {

    public GenerateStatsGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Schedule> scheduleList = model.getScheduleBook().getList();
        AutoCompleteNode<?> statsStartNode = EmptyAutoCompleteNode.getInstance();
        setStartingNode(statsStartNode);
        StatsTypeNode statsTypeNode = new StatsTypeNode(Arrays.asList(StatisticType.values()));
        ScheduleDateNode statsDateNode = new ScheduleDateNode(scheduleList);
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_STAT_TYPE, statsStartNode, statsTypeNode),
                new AutoCompleteEdge<>(PREFIX_STARTING_DATE, statsTypeNode, statsDateNode),
                new AutoCompleteEdge<>(PREFIX_ENDING_DATE, statsDateNode, statsDateNode)
        ));
    }

}
