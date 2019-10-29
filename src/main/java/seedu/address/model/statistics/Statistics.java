package seedu.address.model.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.model.eatery.Category;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Review;
import seedu.address.model.statistics.exceptions.InvalidStatisticsType;

/**
 * This class represents a type of statistic that can be generated from the application.
 */
public class Statistics {
    /**
     * Specifies the type of statistics.
     */
    public enum StatisticsType {
        GRAPH_OVERALL_CATEGORY_TOTAL,
        GRAPH_OVERALL_CATEGORY_AVG; /* TODO: implement GRAPH_OVERALL_DATE_TOTAL, GRAPH_OVERALL_DATE_AVG */
    }

    public static final String MAX_VARIABLE = "max";
    public static final String MIN_VARIABLE = "min";

    private static final String MESSAGE_INVALID_STATS = "Invalid statistics type.";

    private List<Eatery> eateries;
    private TreeMap<Category, List<Eatery>> groupedByCategory;

    public Statistics(List<Eatery> eateries) {
        this.eateries = eateries;
    }

    /**
     * Generates the data needed for the statistics of the type specified by {@link StatisticsType}.
     * @param type the type of statistics to be generated.
     * @return the data needed for the type of statistics stored in a {@link TreeMap}.
     * @throws InvalidStatisticsType if statistics type is invalid.
     */
    public TreeMap<? extends Object, Double> generate(StatisticsType type) throws InvalidStatisticsType {
        try {
            switch (type) {
            case GRAPH_OVERALL_CATEGORY_TOTAL:
                return generateCategoryTotal();

            case GRAPH_OVERALL_CATEGORY_AVG:
                return generateCategoryAvg();

            default:
                throw new InvalidStatisticsType(MESSAGE_INVALID_STATS);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new InvalidStatisticsType(e.getMessage());
        }
    }

    public static HashMap<String, Double> getMaxMinValue(TreeMap<? extends Object, Double> stats) {
        HashMap<String, Double> maxMinMap = new HashMap<>();

        double min = 0;
        double max = 0;
        for (Map.Entry<? extends Object, Double> entry : stats.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }

            if (entry.getValue() < min) {
                min = entry.getValue();
            }
        }

        maxMinMap.put(MAX_VARIABLE, max);
        maxMinMap.put(MIN_VARIABLE, max);
        return maxMinMap;
    }

    /**
     * Groups the eateries by categories; to be used when generating statistics to do with categories.
     */
    private TreeMap<Category, List<Eatery>> groupEaterybyCategory() {
        if (groupedByCategory != null) {
            return groupedByCategory;
        }

        this.groupedByCategory = new TreeMap<Category, List<Eatery>>();
        for (Eatery e : eateries) {
            if (groupedByCategory.containsKey(e.getCategory())) {
                groupedByCategory.get(e.getCategory()).add(e);
            } else {
                groupedByCategory.put(e.getCategory(), new ArrayList<>());
                groupedByCategory.get(e.getCategory()).add(e);
            }
        }

        return groupedByCategory;
    }

    /**
     * Calculates the total amount spent for all categories.
     */
    private TreeMap<Category, Double> generateCategoryTotal() {
        Map<Category, List<Eatery>> duplicateMap = new TreeMap<>(groupEaterybyCategory());
        TreeMap<Category, Double> statsMap = new TreeMap<>();

        for (Map.Entry<Category, List<Eatery>> entry : duplicateMap.entrySet()) {
            double total = 0;

            for (Eatery e : entry.getValue()) {
                for (Review r : e.getReviews()) {
                    total = total + r.getCost();
                }
            }
            statsMap.put(entry.getKey(), total);
        }

        return statsMap;
    }

    /**
     * Calculates the average amount spent for all categories.
     */
    private TreeMap<Category, Double> generateCategoryAvg() {
        Map<Category, List<Eatery>> duplicateMap = new TreeMap<>(groupEaterybyCategory());
        TreeMap<Category, Double> statsMap = new TreeMap<>();

        for (Map.Entry<Category, List<Eatery>> entry : duplicateMap.entrySet()) {
            double totalCost = 0;
            int numberofReviews = 0;

            for (Eatery e : entry.getValue()) {
                numberofReviews = e.getReviews().size();
                for (Review r : e.getReviews()) {
                    totalCost = totalCost + r.getCost();
                }
            }
            statsMap.put(entry.getKey(), totalCost / numberofReviews);
        }
        return statsMap;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Statistics // instanceof handles nulls
                && eateries.equals(((Statistics) other).eateries));
    }
}
