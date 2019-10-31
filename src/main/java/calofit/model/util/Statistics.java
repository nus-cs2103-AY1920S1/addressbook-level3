package calofit.model.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import calofit.model.CalorieBudget;
import calofit.model.dish.Dish;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;

/**
 * Wrapper class that contains all the statistics to be generated in the report.
 */
public class Statistics {

    private final int maximum;
    private final int minimum;
    private final double average;
    private final int calorieExceedCount;
    private final List<Dish> mostConsumedDishes;
    private final ObservableList<Data> pieChartData;
    private final Series<String, Integer> calorieChartData;

    /**
     * Constructor for the wrapper Statistics class that cannot be called by other classes.
     * @param maximum is the maximum Calorie intake of the month.
     * @param minimum is the minimum Calorie intake of the month.
     * @param average is the average Calorie intake per day of the month.
     * @param calorieExceedCount is the number of days of the month where the calorie budget exceeded.
     * @param mostConsumed is the list of dishes that was most consumed in the month.
     * @param pieChartData is the list of meals converted into a list of data to be used in a PieChart.
     * @param calorieData is the data that contains the calories taken over time.
     */
    private Statistics(int maximum, int minimum, double average, int calorieExceedCount, List<Dish> mostConsumed,
                       ObservableList<Data> pieChartData, Series<String, Integer> calorieData) {
        this.maximum = maximum;
        this.minimum = minimum;
        this.average = average;
        this.calorieExceedCount = calorieExceedCount;
        this.mostConsumedDishes = mostConsumed;
        this.pieChartData = pieChartData;
        this.calorieChartData = calorieData;
    }

    /**
     * Factory static method to generate a Statistics wrapper class based on the MealLog for current month.
     * @param mealLog is the MealLog to get the statistics from.
     * @param budget is the CalorieBudget to obtain the history of budgets set by the user.
     * @return a Statistics object that wraps about the statistics generated.
     */
    public static Statistics generateStatistics(MealLog mealLog, CalorieBudget budget) {
        ObservableList<Meal> currentMonthMeals = mealLog.getCurrentMonthMeals();
        ObservableList<Data> pieChartData = Statistics.getPieChartData(currentMonthMeals);
        Series<String, Integer> calorieChartData = Statistics.getCalorieChartData(currentMonthMeals);
        List<Dish> mostConsumed = Statistics.getMostConsumedDishes(currentMonthMeals);
        int calorieExceedCount = Statistics.getCalorieExceedCount(budget, currentMonthMeals);
        int maximum = 0;
        int minimum = 0;
        double average = 0.0;
        for (int i = 1; i <= LocalDate.now().lengthOfMonth(); i++) {
            LocalDate currentDate = LocalDate.now().withDayOfMonth(i);
            int currentCalorieValue = 0;
            ObservableList<Meal> currentDayMeals = currentMonthMeals
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

        average = Math.round(average / LocalDate.now().lengthOfMonth());

        return new Statistics(maximum, minimum, average,
                calorieExceedCount, mostConsumed, pieChartData, calorieChartData);
    }

    /**
     * Returns number of days of current month where calorie intake exceeded calorie budget.
     * @return the number of days.
     */
    public int getCalorieExceedCount() {
        return this.calorieExceedCount;
    }

    /**
     * Method to obtain number of times a calorie budget has been exceeded for that month.
     * Calorie intake for that day is computed by filtering MealLog to obtain that day's meals and
     * summing them up.
     * The calorie intake computed is then compared to that day's calorie budget set by the user.
     * @param budget the calorie budget class that contains the history of budgets set by the user.
     * @param monthlyMeals is the history of meals that the user has eaten for that month.
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
     * Returns the most consumed Dish of the Month.
     * @return a Dish.
     */
    public List<Dish> getMostConsumedDishes() {
        return this.mostConsumedDishes;
    }

    /**
     * Method to obtain the most consumed Dishes in a list of meals
     * Obtained by storing Dishes in a hashmap to check for duplicates and increment how many times they are eaten.
     * @param meals is the list of meals that we want to know the information from.
     * @return the list of most consumed dishes.
     */
    public static List<Dish> getMostConsumedDishes(ObservableList<Meal> meals) {
        HashMap<Dish, Integer> map = new HashMap<>();
        for (int i = 0; i < meals.size(); i++) {
            Dish currentDish = meals.get(i).getDish();
            Integer value = map.get(currentDish);
            map.put(currentDish, value == null ? 1 : value + 1);
        }
        Entry<Dish, Integer> max = null;
        for (Entry<Dish, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue()) {
                max = e;
            }
        }
        Integer maxValue = max.getValue();
        ArrayList<Dish> toBeReturned = new ArrayList<>();
        for (Entry<Dish, Integer> e : map.entrySet()) {
            if (e.getValue() == maxValue) {
                toBeReturned.add(e.getKey());
            }
        }
        return toBeReturned;
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
     * @return the calorie value as an int
     */
    public int getMinimum() {
        return this.minimum;
    }

    /**
     * Gets the average calorie intake of the month.
     * @return the calorie value as an int
     */
    public double getAverage() {
        return this.average;
    }

    /**
     * Generates the list of dishes that the user has eaten this month and number of times each dish has been eaten.
     * Uses a HashMap to track the quantity and for each entry, a Data object is created with the entry name and value.
     * @return the list of dishes eaten this month.
     */
    public static ObservableList<Data> getPieChartData(ObservableList<Meal> meals) {
        ArrayList<Data> data = new ArrayList<>();
        HashMap<Dish, Integer> map = new HashMap<>();
        for (int i = 0; i < meals.size(); i++) {
            Dish currentDish = meals.get(i).getDish();
            Integer value = map.get(currentDish);
            map.put(currentDish, value == null ? 1 : value + 1);
        }
        for (Entry<Dish, Integer> e : map.entrySet()) {
            data.add(new Data(e.getKey().getName().toString() + "\n"
                    + "Number of times eaten: " + e.getValue() + "\n", e.getValue()));
        }
        return FXCollections.observableList(data);
    }

    /**
     * Getter method to return the list of dishes and their quantity as a list of data.
     * @return the list of data containing the dishes eaten this month.
     */
    public ObservableList<Data> getPieChartData() {
        return this.pieChartData;
    }

    /**
     * Method to get the Amount of the Calories taken per day over the month and store them in a series.
     * @param monthlyMeals is the list of meals taken this month.
     * @return the series itself
     */
    public static Series<String, Integer> getCalorieChartData(ObservableList<Meal> monthlyMeals) {
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

    public Series<String, Integer> getCalorieChartData() {
        return this.calorieChartData;
    }
}
