package seedu.eatme.model.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.statistics.exceptions.CannotGenerateStatisticsException;
import seedu.eatme.model.statistics.exceptions.NoAvailableDataException;

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
    private List<Eatery> sortByExpense;
    private List<Eatery> sortByVisit;

    public Statistics(List<Eatery> allEateries) throws NoAvailableDataException, CannotGenerateStatisticsException {
        this.eateries = getEateriesWithReviews(allEateries);

        if (eateries.size() == 0) {
            throw new NoAvailableDataException();
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
            throw new CannotGenerateStatisticsException();
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
     * Generates a list that sorts the eateries by the number of reviews (i.e. visits).
     * @return a list of {@link Eatery} sorted by the number of visits.
     */
    private List<Eatery> sortEateriesByVisit() {
        PriorityQueue<Eatery> sortedVisits = new PriorityQueue<>(eateries.size(), new Comparator<Eatery>() {
            @Override
            public int compare(Eatery e1, Eatery e2) {
                return e1.getNumberOfReviews() == e2.getNumberOfReviews() ? e1.getName().compareTo(e2.getName())
                        : e1.getNumberOfReviews() - e2.getNumberOfReviews();
            }
        });

        sortedVisits.addAll(eateries);

        List<Eatery> sortedVisitsList = new ArrayList<>();
        for (int i = 0; i < eateries.size(); i++) {
            sortedVisitsList.add((Eatery) sortedVisits.poll());
        }

        return sortedVisitsList;
    }

    /**
     * Generates a list that sorts the eateries by the total expense (i.e. total cost for every review in
     * the eatery).
     * @return a list of {@link Eatery} sorted by the total expense.
     */
    private List<Eatery> sortEateriesByExpense() {
        PriorityQueue<Eatery> sortedExpense = new PriorityQueue<>(eateries.size(), new Comparator<Eatery>() {
            @Override
            public int compare(Eatery e1, Eatery e2) {
                double e1Expense = e1.getTotalExpense() / e1.getNumberOfReviews();
                double e2Expense = e2.getTotalExpense() / e2.getNumberOfReviews();

                return e1Expense == e2Expense ? e1.getName().compareTo(e2.getName())
                        : (int) (e1Expense - e2Expense);
            }
        });

        sortedExpense.addAll(eateries);

        List<Eatery> sortedExpenseList = new ArrayList<>();
        for (int i = 0; i < eateries.size(); i++) {
            sortedExpenseList.add((Eatery) sortedExpense.poll());
        }

        return sortedExpenseList;
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
        List<Eatery> sortByExpenseCopy = new ArrayList<>(sortByExpense);
        int size = sortByExpense.size() > 3 ? 3 : sortByExpense.size();

        Collections.reverse(sortByExpenseCopy);
        for (int i = 0; i < size; i++) {
            mostExp.add(sortByExpenseCopy.get(i));
        }

        return mostExp;
    }

    private List<Eatery> getLeastExpEateries() {
        List<Eatery> leastExp = new ArrayList<>();
        int size = sortByExpense.size() > 3 ? 3 : sortByExpense.size();

        for (int i = 0; i < size; i++) {
            leastExp.add(sortByExpense.get(i));
        }

        return leastExp;
    }

    private List<Eatery> getMostVisited() {
        List<Eatery> mostVisited = new ArrayList<>();
        List<Eatery> sortByVisitCopy = new ArrayList<>(sortByVisit);
        int size = sortByVisit.size() > 3 ? 3 : sortByVisit.size();

        Collections.reverse(sortByVisitCopy);
        for (int i = 0; i < size; i++) {
            mostVisited.add(sortByVisitCopy.get(i));
        }

        return mostVisited;
    }

    private List<Eatery> getLeastVisited() {
        List<Eatery> leastVisited = new ArrayList<>();
        int size = sortByVisit.size() > 3 ? 3 : sortByVisit.size();

        for (int i = 0; i < size; i++) {
            leastVisited.add(sortByVisit.get(i));
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
