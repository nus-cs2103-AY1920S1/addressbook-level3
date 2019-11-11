package seedu.address.logic.autocomplete.nodes.stats;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.autocomplete.nodes.AutoCompleteNode;
import seedu.address.logic.commands.statisticcommand.StatisticType;

/**
 * Represents a {@code Node} tracking {@code StatisticType} objects for autocompletion.
 */
public class StatsTypeNode extends AutoCompleteNode<List<StatisticType>> {

    public StatsTypeNode(List<StatisticType> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(statisticType -> values.add(statisticType.toString()));
        return values;
    }

}
