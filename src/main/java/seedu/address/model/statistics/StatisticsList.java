package seedu.address.model.statistics;

import java.util.HashMap;
import java.util.TreeMap;

import seedu.address.model.statistics.Statistics.StatisticsType;

/**
 * This class represents a list to store the all the statistics generated.
 */
public class StatisticsList {
    private HashMap<StatisticsType, TreeMap<? extends Object, Double>> statistics;

    public StatisticsList() {
        this.statistics = new HashMap<StatisticsType, TreeMap<? extends Object, Double>>();
    }

    /**
     * Adds the statistics into the internal {@link HashMap}.
     * @param stats statistics to be added.
     */
    public void addToStatisticsList(StatisticsType type, TreeMap<? extends Object, Double> stats) {
        statistics.put(type, stats);
    }

    /**
     * Returns the contents of the internal {@link HashMap} as a read-only {@link HashMap}.
     */
    public HashMap<StatisticsType, TreeMap<? extends Object, Double>> getStatisticsList() {
        return statistics;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatisticsList // instanceof handles nulls
                && statistics.equals(((StatisticsList) other).statistics));
    }
}
