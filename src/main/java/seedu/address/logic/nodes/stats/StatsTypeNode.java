package seedu.address.logic.nodes.stats;

import seedu.address.logic.commands.statisticcommand.StatisticType;
import seedu.address.logic.Node;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
