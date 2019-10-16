package calofit.model.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedMap;

import javafx.collections.ObservableList;

import calofit.model.CalorieBudget;
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
    private final Meal mostConsumedMeal;

    /**
     * Constructor for thw wrapper Statistics class that cannot be called by other classes.
     *
     * @param maximum is the maximum Calorie intake of the month.
     * @param minimum is the minimum Calorie intake of the month.
     * @param average is the average Calorie intake per day of the month.
     * @param calorieExceedCount is the number of days of the month where the calorie budget exceeded.
     * @param mostConsumed is the Meal that was most consumed in the month.
     */
    private Statistics(int maximum, int minimum, double average, int calorieExceedCount, Meal mostConsumed) {
        this.maximum = maximum;
        this.minimum = minimum;
        this.average = average;
        this.calorieExceedCount = calorieExceedCount;
        this.mostConsumedMeal = mostConsumed;
    }

    /**
     * Factory static method to generate a Statistics wrapper class based on the MealLog for current month.
     *
     * @param mealLog is the MealLog to get the statistics from.
     * @param budget is the CalorieBudget to obtain the history of budgets set by the user.
     * @return a Statistics object that wraps about the statistics generated.
     */
    public static Statistics generateStatistics(MealLog mealLog, CalorieBudget budget) {
        ObservableList<Meal> currentMonthMeals = mealLog.getCurrentMonthMeals();
        Meal mostConsumed = Statistics.getMostConsumedMeal(currentMonthMeals);
        int calorieExceedCount = Statistics.getCalorieExceedCount(budget, currentMonthMeals);
        int maximum = currentMonthMeals.get(0).getDish().getCalories().getValue();
        int minimum = currentMonthMeals.get(0).getDish().getCalories().getValue();
        int average = currentMonthMeals.get(0).getDish().getCalories().getValue();
        Month currentMonth = currentMonthMeals.get(0).getTimestamp().getDateTime().getMonth();

        for (int i = 1; i < currentMonthMeals.size(); i++) {
            Meal current = currentMonthMeals.get(i);
            int currentCalories = current.getDish().getCalories().getValue();
            if (current.getTimestamp().getDateTime().getMonth() != currentMonth) {
                break;
            } else {
                if (currentCalories > maximum) {
                    maximum = currentCalories;
                }
                if (currentCalories < minimum) {
                    minimum = currentCalories;
                }
                average += currentCalories;
            }
        }

        average = average / currentMonthMeals.size();

        return new Statistics(maximum, minimum, average, calorieExceedCount, mostConsumed);
    }

    public int getCalorieExceedCount() {
        return this.calorieExceedCount;
    }

    /**
     * Method to obtain number of times a calorie budget has been exceeded for that month.
     * Calorie intake for that day is computed by filtering MealLog to obtain that day's meals and
     * summing them up.
     * The calorie intake computed is then compared to that day's calorie budget set by the user.
     *
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

    public Meal getMostConsumedMeal() {
        return this.mostConsumedMeal;
    }

    /**
     * Method to obtain the most consumed meal in a list of meals
     * Obtained by storing meals in a hashmap to check for duplicates and increment how many times they are eaten.
     *
     * @param meals is the list of meals that we want to know the information from.
     * @return the most consumed meal in the list.
     */
    public static Meal getMostConsumedMeal(ObservableList<Meal> meals) {
        HashMap<Meal, Integer> map = new HashMap<>();
        for (int i = 0; i < meals.size(); i++) {
            Meal currentMeal = meals.get(i);
            Integer value = map.get(currentMeal);
            map.put(currentMeal, value == null ? 1 : value + 1);
        }
        Entry<Meal, Integer> max = null;
        for (Entry<Meal, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue()) {
                max = e;
            }
        }
        return max.getKey();
    }

    public int getMaximum() {
        return this.maximum;
    }

    public int getMinimum() {
        return this.minimum;
    }

    public double getAverage() {
        return this.average;
    }

}
