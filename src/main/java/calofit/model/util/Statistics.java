package calofit.model.util;

import java.time.Month;
import java.util.HashMap;
import java.util.Map.Entry;

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
     * @param meaLog is the MealLog to get the statistics from.
     * @return a Statistics object that wraps about the statistics generated.
     */
    public static Statistics generateStatistics(MealLog meaLog) {
        ObservableList<Meal> meals = meaLog.getMeals();
        Meal mostConsumed = Statistics.getMostConsumedMeal(meals);
        int maximum = meals.get(0).getDish().getCalories().getValue();
        int minimum = meals.get(0).getDish().getCalories().getValue();
        int average = meals.get(0).getDish().getCalories().getValue();
        Month currentMonth = meals.get(0).getTimestamp().getDateTime().getMonth();

        for (int i = 1; i < meals.size(); i++) {
            Meal current = meals.get(i);
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

        average = average / meals.size();

        return new Statistics(maximum, minimum, average, 0, mostConsumed);
    }

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

    public int getCalorieExceedCount() {
        return this.calorieExceedCount;
    }

    public Meal getMostConsumedMeal() {
        return this.mostConsumedMeal;
    }
}
