package seedu.address.logic.autocomplete.graphs.stats;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAT_TYPE;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.autocomplete.graphs.Edge;
import seedu.address.logic.autocomplete.graphs.GraphWithStartNode;
import seedu.address.logic.autocomplete.nodes.schedule.ScheduleDateNode;
import seedu.address.logic.autocomplete.nodes.stats.StatsTypeNode;
import seedu.address.logic.commands.statisticcommand.StatisticType;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code StatsCommand}.
 */
public class GenerateStatsGraph extends GraphWithStartNode {

    public GenerateStatsGraph(Model model) {
        super();
        initialise(model);
    }

    /**
     * Initialises this graph's {@code Node}s.
     */
    private void initialise(Model model) {
        List<Schedule> scheduleList = model.getScheduleBook().getList();
        StatsTypeNode statsTypeNode = new StatsTypeNode(Arrays.asList(StatisticType.values()));
        ScheduleDateNode statsDateNode = new ScheduleDateNode(scheduleList);
        addEdges(
                new Edge<>(PREFIX_STAT_TYPE, startingNode, statsTypeNode),
                new Edge<>(PREFIX_STARTING_DATE, statsTypeNode, statsDateNode),
                new Edge<>(PREFIX_ENDING_DATE, statsDateNode, statsDateNode)
        );
    }

}
