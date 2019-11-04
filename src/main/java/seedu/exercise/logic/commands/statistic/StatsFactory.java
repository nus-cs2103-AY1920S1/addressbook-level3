package seedu.exercise.logic.commands.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.resource.Exercise;

/**
 * A class to generate Statistic depending on the chart type.
 */
public class StatsFactory {

    private static final String DEFAULT_BAR_CHART = "barchart";
    private static final String DEFAULT_LINE_CHART = "linechart";
    private static final String DEFAULT_PIE_CHART = "piechart";
    private static final String DEFAULT_CALORIES = "calories";
    private static final String DEFAULT_EXERCISE = "exercise";

    private static final Logger logger = LogsCenter.getLogger(StatsFactory.class);
    private ObservableList<Exercise> exercises;
    private String chart;
    private String category;
    private Date startDate;
    private Date endDate;

    /**
     * Generates a StatsFactory object that can generate Statistic.
     * If start date and end date is not given,
     * it will set end date to today's date and start date to be one week before.
     */
    public StatsFactory(ReadOnlyResourceBook<Exercise> exercises, String chart, String category,
                        Date startDate, Date endDate) {
        this.exercises = exercises.getSortedResourceList();
        this.chart = chart;
        this.category = category;
        if (startDate == null && endDate == null) {
            this.startDate = Date.getOneWeekBeforeToday();
            this.endDate = Date.getToday();
        } else {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    /**
     * Generates and returns statistic for different chart type.
     */
    public Statistic generateStatistic() {
        switch(chart) {

        case DEFAULT_PIE_CHART:
            return generatePieChartStatistic();

        case DEFAULT_LINE_CHART:
            return generateLineChartStatistic();

        case DEFAULT_BAR_CHART:
            return generateBarChartStatistic();

        default:
            logger.fine("Chart type is not correct. Default chart will be displayed.");
            return getDefaultStatistic();
        }
    }

    /**
     * Generate statistic for line chart.
     */
    private Statistic generateLineChartStatistic() {
        ArrayList<Date> dates = Date.getListOfDates(startDate, endDate);
        ArrayList<Double> values;

        if (category.equals(DEFAULT_EXERCISE)) {
            values = exerciseFrequencyByDate(getFilteredExercise(), dates);
        } else {
            values = caloriesByDate(getFilteredExercise(), dates);
        }

        double total = getTotal(values);
        double average = getAverage(total);

        return new Statistic(category, chart, startDate, endDate, datesToString(dates), values, total, average);
    }

    /**
     * Generate statistic for bar chart.
     */
    private Statistic generateBarChartStatistic() {
        HashMap<String, Double> data;
        if (category.equals(DEFAULT_EXERCISE)) {
            data = getTotalExerciseFrequency();
        } else { //calories
            data = getTotalCaloriesData();
        }

        ArrayList<String> names = hashMapNameToList(data);
        ArrayList<Double> values = hashMapDoubleToList(data, names);
        double total = getTotal(values);
        double average = getAverage(total);

        return new Statistic(category, chart, startDate, endDate, names, values, total, average);
    }

    /**
     * Generate statistic for pie chart.
     */
    private Statistic generatePieChartStatistic() {
        HashMap<String, Double> data;
        if (category.equals(DEFAULT_EXERCISE)) {
            data = getTotalExerciseFrequency();
        } else { //calories
            data = getTotalCaloriesData();
        }

        ArrayList<String> names = hashMapNameToList(data);
        ArrayList<Double> values = hashMapDoubleToList(data, names);
        double total = getTotal(values);
        double average = getAverage(total);

        return new Statistic(category, chart, startDate, endDate, names, values, total, average);
    }

    /**
     * Returns the sum of all values.
     */
    private double getTotal(ArrayList<Double> values) {
        double total = 0;
        for (double d : values) {
            total += d;
        }
        return total;
    }

    /**
     * Returns the average value.
     */
    private double getAverage(double total) {
        int numberOfDays = Date.numberOfDaysBetween(startDate, endDate) + 1;
        return total / numberOfDays;
    }

    /**
     * Compute exercise count with filtered exercises list.
     */
    private HashMap<String, Double> getTotalExerciseFrequency() {
        ArrayList<Exercise> filteredExercise = getFilteredExercise();
        HashMap<String, Double> data = new HashMap<>();

        for (Exercise e : filteredExercise) {
            Name name = e.getName();
            Unit unit = e.getUnit();
            String nameWithUnit = name.toString() + " (" + unit.toString() + ")";
            double count = 1;

            if (data.containsKey(nameWithUnit)) {
                count = data.get(nameWithUnit) + 1;
            }

            data.put(nameWithUnit, count);
        }

        return data;
    }

    /**
     * Compute calories with filtered exercises list.
     */
    private HashMap<String, Double> getTotalCaloriesData() {
        ArrayList<Exercise> filteredExercise = getFilteredExercise();
        HashMap<String, Double> data = new HashMap<>();

        for (Exercise e : filteredExercise) {
            String nameWithUnit = e.getName().toString() + " (" + e.getUnit().toString() + ")";
            double calories = Double.parseDouble(e.getCalories().toString());

            if (data.containsKey(nameWithUnit)) {
                double temp = data.get(nameWithUnit);
                calories += temp;
            }

            data.put(nameWithUnit, calories);
        }

        return data;
    }

    /**
     * Get all exercises between start and end date.
     */
    private ArrayList<Exercise> getFilteredExercise() {
        ArrayList<Exercise> filteredExercise = new ArrayList<>();
        for (Exercise e : exercises) {
            Date date = e.getDate();
            if (Date.isBetweenStartAndEndDate(date, startDate, endDate)) {
                filteredExercise.add(e);
            }
        }

        return filteredExercise;
    }

    /**
     * Get list of names from data computed.
     */
    private ArrayList<String> hashMapNameToList(HashMap<String, Double> data) {
        return new ArrayList<>(data.keySet());
    }

    /**
     * Get list of values from data computed.
     */
    private ArrayList<Double> hashMapDoubleToList(HashMap<String, Double> data, ArrayList<String> names) {
        ArrayList<Double> values = new ArrayList<>();

        for (String n : names) {
            values.add(data.get(n));
        }

        return values;
    }

    /**
     * Convert list of dates to list of Strings.
     */
    private ArrayList<String> datesToString(ArrayList<Date> dates) {
        ArrayList<String> list = new ArrayList<>();
        for (Date d : dates) {
            list.add(d.toString());
        }
        return list;
    }

    /**
     * Generate a list of given size with all zeroes.
     */
    private ArrayList<Double> listWithZeroes(int listSize) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(0.0);
        }
        return list;
    }

    /**
     * Compute exercise count by dates with filtered exercises list.
     */
    private ArrayList<Double> exerciseFrequencyByDate(ArrayList<Exercise> exercises, ArrayList<Date> dates) {

        int size = dates.size();
        ArrayList<Double> values = listWithZeroes(size);

        for (Exercise e : exercises) {
            Date date = e.getDate();
            int index = dates.indexOf(date);
            double quantity = values.get(index) + 1;
            values.set(index, quantity);
        }

        return values;
    }

    /**
     * Compute calories by date with filtered exercises list.
     */
    private ArrayList<Double> caloriesByDate(ArrayList<Exercise> exercises, ArrayList<Date> dates) {

        int size = dates.size();
        ArrayList<Double> values = listWithZeroes(size);

        for (Exercise e : exercises) {
            Date date = e.getDate();
            int index = dates.indexOf(date);
            double calories = Double.parseDouble(e.getCalories().toString()) + values.get(index);
            values.set(index, calories);
        }

        return values;
    }

    /**
     * Returns line chart of calories of the most recent 7 days.
     */
    public Statistic getDefaultStatistic() {
        ArrayList<Exercise> filteredExercise = getFilteredExercise();
        ArrayList<Date> dates = Date.getListOfDates(startDate, endDate);
        ArrayList<String> properties = datesToString(dates);
        ArrayList<Double> values = caloriesByDate(filteredExercise, dates);
        double total = getTotal(values);
        double average = getAverage(total);
        return new Statistic(DEFAULT_CALORIES, DEFAULT_LINE_CHART, startDate, endDate,
                properties, values, total, average);
    }
}
