package calofit.model.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import calofit.commons.core.LogsCenter;
import calofit.model.CalorieBudget;
import calofit.model.dish.Dish;
import calofit.model.meal.Meal;

/**
 * Wrapper class that contains all the statistics to be generated in the report.
 * Statistics to be generated are calculated and stored in the wrapper object as data attributes.
 */
public class Statistics {

    private static final Logger logger = LogsCenter.getLogger(Statistics.class);

    private final int maximum;
    private final int minimum;
    private final double average;
    private final int calorieExceedCount;
    private final List<Dish> mostConsumedDishes;
    private final ObservableList<Data> pieChartData;
    private final Series<String, Integer> calorieChartSeries;

    /**
     * Constructor for the wrapper Statistics class that cannot be called by other classes.
     * @param maximum is the maximum Calorie intake of the month.
     * @param minimum is the minimum Calorie intake of the month.
     * @param average is the average Calorie intake per day of the month.
     * @param calorieExceedCount is the number of days of the month where the calorie budget was exceeded.
     * @param mostConsumedDishes is the list of dishes that was most consumed in the month.
     * @param pieChartData is the data that contains the quantity of each dish eaten this month.
     * @param calorieSeries is the series that represents the data showing calories taken over time for this month.
     */
    private Statistics(int maximum, int minimum, double average, int calorieExceedCount, List<Dish> mostConsumedDishes,
                       ObservableList<Data> pieChartData, Series<String, Integer> calorieSeries) {

        assert maximum >= 0 : "Negative maximum calorie value is invalid.";
        assert minimum >= 0 : "Negative minimum calorie value is invalid.";
        assert average >= 0 : "Negative average calorie value is invalid.";
        assert calorieExceedCount >= 0 : "Negative number of days calorie intake exceeded budget is invalid.";
        assert mostConsumedDishes.size() > 0 : "Empty meal log resulted in empty list of most consumed dishes.";
        assert pieChartData.size() > 0 : "Empty meal log resulted in no data obtained for the pie chart.";
        assert calorieSeries.getData().size() > 0 : "Empty meal log resulted in no calorie intake value processed.";

        this.maximum = maximum;
        this.minimum = minimum;
        this.average = average;
        this.calorieExceedCount = calorieExceedCount;
        this.mostConsumedDishes = mostConsumedDishes;
        this.pieChartData = pieChartData;
        this.calorieChartSeries = calorieSeries;

    }

    /**
     * Factory static method to create the Statistics object based on the {@code MealLog} and {@code CalorieBudget}.
     * @param currentMonthMealLog is the MealLog that contains the list of meals which we want to gather the data from.
     * @param budget is the CalorieBudget that contains the history of budgets which we want to gather the data from.
     * @return a Statistics object that wraps about the statistics generated.
     */
    public static Statistics generateStatistics(ObservableList<Meal> currentMonthMealLog, CalorieBudget budget) {
        logger.fine("Statistics are being generated.");

        try {
            requireNonNull(budget);
        } catch (NullPointerException nullParams) {
            logger.fine("Null CalorieBudget cannot be processed to get statistics.");
        }
        assert !currentMonthMealLog.isEmpty() : "Empty current month meal log not handled.";

        int calorieExceedCount = Statistics.getCalorieExceedCount(budget, currentMonthMealLog);
        List<Dish> mostConsumedDishes = Statistics.getMostConsumedDishes(currentMonthMealLog);
        ObservableList<Data> pieChartData = Statistics.getPieChartData(currentMonthMealLog);
        Series<String, Integer> calorieChartData = Statistics.getCalorieChartSeries(currentMonthMealLog);

        int maximum = 0;
        int minimum = 0;
        double average = 0.0;
        for (int i = 1; i <= LocalDate.now().lengthOfMonth(); i++) {
            LocalDate currentDate = LocalDate.now().withDayOfMonth(i);
            int currentCalorieValue = 0;
            ObservableList<Meal> currentDayMeals = currentMonthMealLog
                    .filtered(meal -> meal.getTimestamp().getDateTime().toLocalDate().equals(currentDate));
            for (int j = 0; j < currentDayMeals.size(); j++) {
                Meal currentMeal = currentDayMeals.get(j);
                currentCalorieValue += currentMeal.getDish().getCalories().getValue();
            }
            average += currentCalorieValue;
            if (currentCalorieValue > maximum) {
                maximum = currentCalorieValue;
            }
            if (currentCalorieValue < minimum) {
                minimum = currentCalorieValue;
            }
        }

        average = Math.round(average / ((double) LocalDate.now().lengthOfMonth()));

        return new Statistics(maximum, minimum, average,
                calorieExceedCount, mostConsumedDishes, pieChartData, calorieChartData);
    }

    /**
     * Gets the maximum calorie intake of the month.
     * @return the calorie value as an int
     */
    public int getMaximum() {
        return this.maximum;
    }

    /**
     * Gets the minimum calorie intake of the month.
     * @return the calorie value as an int.
     */
    public int getMinimum() {
        return this.minimum;
    }

    /**
     * Gets the average calorie intake of the month.
     * @return the calorie value as an double.
     */
    public double getAverage() {
        return this.average;
    }

    /**
     * Returns the number of days of the current month where calorie intake exceeded calorie budget.
     * @return the number of days.
     */
    public int getCalorieExceedCount() {
        return this.calorieExceedCount;
    }

    /**
     * Method to obtain the number of times the calorie budget has been exceeded for that month.
     * Calorie intake for a day is computed by filtering {@code MealLog} to obtain that day's meals and
     * summing the calorie values of the {@code Dish} that make up the {@code Meal}.
     * The calorie intake computed is then compared to that day's calorie budget set by the user.
     * @param budget the {@code CalorieBudget} that contains the history of budgets set by the user.
     * @param monthlyMeals is the list of meals that the user has eaten for that month.
     * @return the number of days where the calorie intake exceeded the calorie budget.
     */
    public static int getCalorieExceedCount(CalorieBudget budget, ObservableList<Meal> monthlyMeals) {
        SortedMap<LocalDate, Integer> currentMonthBudget = budget.getCurrentMonthBudgetHistory();
        int calorieExceedCount = 0;
        for (int i = 1; i <= LocalDate.now().lengthOfMonth(); i++) {
            LocalDate currentDate = LocalDate.now().withDayOfMonth(i);
            Integer currentCalorieBudget = currentMonthBudget.get(currentDate);
            if (currentCalorieBudget == null) {
                continue;
            }
            int currentCalorieValue = 0;
            ObservableList<Meal> currentDayMeals = monthlyMeals
                    .filtered(meal -> meal.getTimestamp().getDateTime().toLocalDate().equals(currentDate));
            for (int j = 0; j < currentDayMeals.size(); j++) {
                currentCalorieValue += currentDayMeals.get(j).getDish().getCalories().getValue();
            }
            if (currentCalorieValue > currentCalorieBudget) {
                calorieExceedCount++;
            }
        }
        return calorieExceedCount;
    }

    /**
     * Returns the most consumed {@code Dish} of the month in a list to cater to multiple dishes.
     * @return a list of dishes.
     */
    public List<Dish> getMostConsumedDishes() {
        return this.mostConsumedDishes;
    }

    /**
     * Method to obtain the most consumed {@code Dish} or Dishes of the month.
     * Obtained by storing Dishes in a {@code HashMap} to check for duplicates and
     * incrementing the value everytime the dish is processed.
     * @param meals is the list of meals that we want to know the information from.
     * @return the list of most consumed dishes.
     */
    public static List<Dish> getMostConsumedDishes(ObservableList<Meal> meals) {
        HashMap<Dish, Integer> mapOfDishes = new HashMap<>();
        for (int i = 0; i < meals.size(); i++) {
            Dish currentDish = meals.get(i).getDish();
            Integer value = mapOfDishes.get(currentDish);
            mapOfDishes.put(currentDish, value == null ? 1 : value + 1);
        }
        Entry<Dish, Integer> maxEntry = null;
        for (Entry<Dish, Integer> currentEntry : mapOfDishes.entrySet()) {
            if (maxEntry == null || currentEntry.getValue() > maxEntry.getValue()) {
                maxEntry = currentEntry;
            }
        }
        Integer maxValue = maxEntry.getValue();
        ArrayList<Dish> listOfMostConsumedDishes = new ArrayList<>();
        for (Entry<Dish, Integer> currentEntry : mapOfDishes.entrySet()) {
            if (currentEntry.getValue() == maxValue) {
                listOfMostConsumedDishes.add(currentEntry.getKey());
            }
        }
        return listOfMostConsumedDishes;
    }

    /**
     * Getter method to return the list of dishes and their quantity consumed for this month as a list of data.
     * @return the list of data containing the dishes eaten this month and their respective quantities.
     */
    public ObservableList<Data> getPieChartData() {
        return this.pieChartData;
    }

    /**
     * Generates the list of dishes that the user has eaten this month and the number of times each was eaten.
     * Obtained by storing the {@code Dish} in a {@code HashMap} to check for duplicates and
     * incrementing the value everytime the dish is processed.
     * For each {@code Entry} generated from the {@code EntrySet} of the HashMap,
     * a {@code Data} object is created with the entry name and value.
     * @param meals is the list of meals that we want to know the information from.
     * @return the list of data containing each dish and their quantity eaten this month.
     */
    public static ObservableList<Data> getPieChartData(ObservableList<Meal> meals) {
        ArrayList<Data> data = new ArrayList<>();
        HashMap<Dish, Integer> mapOfDishes = new HashMap<>();
        for (int i = 0; i < meals.size(); i++) {
            Dish currentDish = meals.get(i).getDish();
            Integer value = mapOfDishes.get(currentDish);
            mapOfDishes.put(currentDish, value == null ? 1 : value + 1);
        }
        for (Entry<Dish, Integer> currentEntry : mapOfDishes.entrySet()) {
            data.add(new Data(currentEntry.getKey().getName().toString() + "\n"
                    + "Number of times eaten: " + currentEntry.getValue() + "\n", currentEntry.getValue()));
        }
        return FXCollections.observableList(data);
    }

    /**
     * Getter method to return the series of calorie intake value of the user over the course of the month.
     * @return the series representing the above data.
     */
    public Series<String, Integer> getCalorieChartSeries() {
        return this.calorieChartSeries;
    }

    /**
     * Method to get the amount of calories taken per day over the month and store them in a series.
     * Calorie intake for a day is computed by filtering monthlyMeals to obtain that day's meals and
     * summing the calorie values of the {@code Dish} that make up the {@code Meal}.
     * A {@code Data} object is then added to the series containing the date of the month and the
     * calorie intake value calculated for that date.
     * @param monthlyMeals is the list of meals taken this month.
     * @return the series itself.
     */
    public static Series<String, Integer> getCalorieChartSeries(ObservableList<Meal> monthlyMeals) {
        XYChart.Series<String, Integer> calorieData = new XYChart.Series<>();
        calorieData.setName("Date");
        for (int i = 1; i <= LocalDate.now().lengthOfMonth(); i++) {
            int currentCalorieValue = 0;
            LocalDate currentDate = LocalDate.now().withDayOfMonth(i);
            ObservableList<Meal> currentDayMeals = monthlyMeals
                    .filtered(meal -> meal.getTimestamp().getDateTime().toLocalDate().equals(currentDate));
            for (int j = 0; j < currentDayMeals.size(); j++) {
                currentCalorieValue += currentDayMeals.get(j).getDish().getCalories().getValue();
            }
            calorieData.getData().add(new XYChart.Data(String.valueOf(i), currentCalorieValue));
        }
        return calorieData;
    }

}
