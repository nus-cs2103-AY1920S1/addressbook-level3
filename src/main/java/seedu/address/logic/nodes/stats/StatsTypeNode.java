package seedu.address.logic.nodes.stats;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.Node;
import seedu.address.logic.commands.statisticcommand.StatisticType;

/**
 * Represents a {@code Node} tracking {@code StatisticType} objects for autocompletion.
 */
public class StatsTypeNode extends Node<StatisticType> {

    public StatsTypeNode(List<StatisticType> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(statisticType -> values.add(statisticType.toString()));
        return values;
    }

}
