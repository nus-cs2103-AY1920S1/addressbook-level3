package seedu.address.model.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import seedu.address.model.eatery.Category;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.statistics.exceptions.CannotGenerateStatistics;
import seedu.address.model.statistics.exceptions.NoAvailableData;

/**
 * This class represents a type of statistic that can be generated from the application.
 */
public class Statistics {
    public static final String MAX_VARIABLE = "max";
    public static final String MIN_VARIABLE = "min";

    public final TreeMap<Category, Double> graphCategoryAvgExpense;
    public final TreeMap<Category, Double> graphCategoryTotalExpense;
    public final TreeMap<Category, Integer> chartCategoryTotalVisited;
    public final List<Eatery> mostExpEatery;
    public final List<Eatery> leastExpEatery;
    public final List<Eatery> mostVisitedEatery;
    public final List<Eatery> leastVisitedEatery;

    private List<Eatery> eateries;
    private TreeMap<Category, List<Eatery>> groupByCategory;
    private PriorityQueue<Eatery> sortByExpense;
    private PriorityQueue<Eatery> sortByVisit;

    public Statistics(List<Eatery> allEateries) throws NoAvailableData, CannotGenerateStatistics {
        this.eateries = getEateriesWithReviews(allEateries);

        if (eateries.size() == 0) {
            throw new NoAvailableData("No available data to create statistics currently; try adding some reviews.");
        }

        // needed for stats
        this.groupByCategory = groupEaterybyCategory();
        this.sortByExpense = sortEateriesByExpense();
        this.sortByVisit = sortEateriesByVisit();

        // generate stats
        try {
            this.graphCategoryAvgExpense = generateCategoryCostAvg();
            this.graphCategoryTotalExpense = generateCategoryCostTotal();
            this.chartCategoryTotalVisited = generateCategoryVisit();
            this.mostExpEatery = getMostExpEateries();
            this.leastExpEatery = getLeastExpEateries();
            this.mostVisitedEatery = getMostVisited();
            this.leastVisitedEatery = getLeastVisited();

        } catch (Exception e) {
            throw new CannotGenerateStatistics("Unable to generate statistics due to unknown error.");
        }
    }

    public static List<Eatery> getEateriesWithReviews(List<Eatery> eateries) {
        List<Eatery> filteredEateries = new ArrayList<>();
        for (Eatery e : eateries) {
            if (e.getNumberOfReviews() > 0) {
                filteredEateries.add(e);
            }
        }

        return filteredEateries;
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
        maxMinMap.put(MIN_VARIABLE, min);
        return maxMinMap;
    }

    /**
     * Groups the eateries by categories; to be used when generating statistics to do with categories.
     */
    private TreeMap<Category, List<Eatery>> groupEaterybyCategory() {
        TreeMap<Category, List<Eatery>> groupedByCategory = new TreeMap<>();

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
     * Generates a priority queue that sorts the eateries by the number of reviews (i.e. visits).
     * @return a priority queue of {@link Eatery} sorted by the number of visits.
     */
    private PriorityQueue sortEateriesByVisit() {
        PriorityQueue sortedVisits = new PriorityQueue(eateries.size(), new Comparator<Eatery>() {
            @Override
            public int compare(Eatery e1, Eatery e2) {
                return e1.getNumberOfReviews() == e2.getNumberOfReviews() ? e1.getName().compareTo(e2.getName())
                        : e1.getNumberOfReviews() > e2.getNumberOfReviews() ? e1.getNumberOfReviews()
                        : e2.getNumberOfReviews();
            }
        });

        for (Eatery e : eateries) {
            sortedVisits.add(e);
        }

        return sortedVisits;
    }

    /**
     * Generates a priority queue that sorts the eateries by the total expense (i.e. total cost for every review in
     * the eatery).
     * @return a priority queue of {@link Eatery} sorted by the total expense.
     */
    private PriorityQueue sortEateriesByExpense() {
        PriorityQueue sortedExpense = new PriorityQueue(eateries.size(), new Comparator<Eatery>() {
            @Override
            public int compare(Eatery e1, Eatery e2) {
                double e1Expense = e1.getTotalExpense() / e1.getNumberOfReviews();
                double e2Expense = e2.getTotalExpense() / e2.getNumberOfReviews();

                return e1Expense == e2Expense ? e1.getName().compareTo(e2.getName())
                        : e1Expense > e2Expense ? (int) e1Expense
                        : (int) e2Expense;
            }
        });

        for (Eatery e : eateries) {
            sortedExpense.add(e);
        }

        return sortedExpense;
    }

    /**
     * Calculates the total amount spent for all categories; used to create the charts.
     */
    private TreeMap<Category, Double> generateCategoryCostTotal() {
        Map<Category, List<Eatery>> duplicateMap = new TreeMap<>(groupEaterybyCategory());
        TreeMap<Category, Double> statsMap = new TreeMap<>();

        for (Map.Entry<Category, List<Eatery>> entry : duplicateMap.entrySet()) {
            double total = 0;
            for (Eatery e : entry.getValue()) {
                total = total + e.getTotalExpense();
            }

            statsMap.put(entry.getKey(), total);
        }
        return statsMap;
    }

    /**
     * Calculates the average amount spent for all categories; used to create the charts.
     */
    private TreeMap<Category, Double> generateCategoryCostAvg() {
        Map<Category, List<Eatery>> duplicateMap = new TreeMap<>(groupEaterybyCategory());
        TreeMap<Category, Double> statsMap = new TreeMap<>();

        for (Map.Entry<Category, List<Eatery>> entry : duplicateMap.entrySet()) {
            double total = 0;
            int numberOfReviews = 0;
            for (Eatery e : entry.getValue()) {
                numberOfReviews = e.getNumberOfReviews();
                total = total + e.getTotalExpense();
            }

            statsMap.put(entry.getKey(), total / numberOfReviews);
        }
        return statsMap;
    }

    /**
     * Calculates the total number of visits to eateries in the different categories; used to create the charts.
     */
    private TreeMap<Category, Integer> generateCategoryVisit() {
        Map<Category, List<Eatery>> duplicateMap = new TreeMap<>(groupEaterybyCategory());
        TreeMap<Category, Integer> statsMap = new TreeMap<>();

        for (Map.Entry<Category, List<Eatery>> entry : duplicateMap.entrySet()) {
            int numberOfVisits = 0;
            for (Eatery e : entry.getValue()) {
                numberOfVisits = numberOfVisits + e.getNumberOfReviews();
            }

            statsMap.put(entry.getKey(), numberOfVisits);
        }
        return statsMap;
    }

    private List<Eatery> getMostExpEateries() {
        List<Eatery> mostExp = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mostExp.add(sortByExpense.poll());
        }

        Collections.reverse(mostExp);
        return mostExp;
    }

    private List<Eatery> getLeastExpEateries() {
        List<Eatery> leastExp = new ArrayList<>();
        Iterator value = sortByExpense.iterator();

        int i = 0;
        while (value.hasNext()) {
            i = i + 1;
            Eatery e = (Eatery) value.next();

            if (i >= eateries.size() - 5) {
                leastExp.add(e);
            }
        }

        return leastExp;
    }

    private List<Eatery> getMostVisited() {
        List<Eatery> mostVisited = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mostVisited.add(sortByVisit.poll());
        }

        Collections.reverse(mostVisited);
        return mostVisited;
    }

    private List<Eatery> getLeastVisited() {
        List<Eatery> leastVisited = new ArrayList<>();
        Iterator value = sortByVisit.iterator();

        int i = 0;
        while (value.hasNext()) {
            i = i + 1;
            Eatery e = (Eatery) value.next();

            if (i >= eateries.size() - 5) {
                leastVisited.add(e);
            }
        }

        return leastVisited;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Statistics // instanceof handles nulls
                && eateries.equals(((Statistics) other).eateries));
    }
}
