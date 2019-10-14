package calofit.model.util;

import java.time.Month;

import javafx.collections.ObservableList;

import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;

/**
 * Wrapper class that contains all the statistics to be generated in the report.
 */
public class Statistics {

    private final int maximum;
    private final int minimum;
    private final double average;

    /**
     * Constructor for thw wrapper Statistics class that cannot be called by other classes.
     *
     * @param maximum is the maximum Calorie intake of the month.
     * @param minimum is the minimum Calorie intake of the month.
     * @param average is the average Calorie intake per day of the month.
     */
    private Statistics(int maximum, int minimum, double average) {
        this.maximum = maximum;
        this.minimum = minimum;
        this.average = average;
    }

    /**
     * Factory static method to generate a Statistics wrapper class based on the MealLog for current month.
     *
     * @param meaLog is the MealLog to get the statistics from.
     * @return a Statistics object that wraps about the statistics generated.
     */
    public static Statistics generateStatistics(MealLog meaLog) {
        ObservableList<Meal> meals = meaLog.getMeals();
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
                average = (average + currentCalories) / (i + 1);
            }
        }

        return new Statistics(maximum, minimum, average);
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
