package seedu.exercise.logic.commands.statistic;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.exercise.model.property.Date;

/**
 * Represents a Statistic with data needed to generate chart.
 */
public class Statistic {

    public static final String MESSAGE_INVALID_CATEGORY = "Category can only be \'exercise\' or \'calories\'";
    public static final String MESSAGE_INVALID_CHART_TYPE = "Chart type can only be \'piechart\' or "
             + "\'linechart\' or \'barchart\'";

    private String category;
    private String chart;
    private Date startDate;
    private Date endDate;
    private ArrayList<String> properties;
    private ArrayList<Double> values;
    private double total;
    private double average;

    /**
     * Every field must be present and not null.
     */
    public Statistic(String category, String chart, Date startDate, Date endDate,
                     ArrayList<String> properties, ArrayList<Double> values, double total, double average) {
        requireAllNonNull(category, chart, startDate, endDate, properties, values);
        this.category = category;
        this.chart = chart;
        this.startDate = startDate;
        this.endDate = endDate;
        this.properties = properties;
        this.values = values;
        this.total = total;
        this.average = average;
    }

    /**
     * Resets the existing data of this {@code Statistic} with {@code newStatistic}.
     */
    public void resetData(Statistic newStatistic) {
        requireNonNull(newStatistic);
        setCategory(newStatistic.getCategory());
        setChart(newStatistic.getChart());
        setStartDate(newStatistic.getStartDate());
        setEndDate(newStatistic.getEndDate());
        setProperties(newStatistic.getProperties());
        setValues(newStatistic.getValues());
        setTotal(newStatistic.getTotal());
        setAverage(newStatistic.getAverage());
    }

    /**
     * Calculates and returns the percentage of value.
     */
    public static double percentage(double value, double total) {
        return value / total * 100;
    }

    private void setCategory(String category) {
        requireNonNull(category);
        this.category = category;
    }

    private void setChart(String chart) {
        requireNonNull(chart);
        this.chart = chart;
    }

    private void setStartDate(Date startDate) {
        requireNonNull(startDate);
        this.startDate = startDate;
    }

    private void setEndDate(Date endDate) {
        requireNonNull(endDate);
        this.endDate = endDate;
    }

    private void setProperties(ArrayList<String> properties) {
        requireNonNull(properties);
        this.properties = properties;
    }

    private void setValues(ArrayList<Double> values) {
        requireNonNull(values);
        this.values = values;
    }

    private void setTotal(double total) {
        requireNonNull(total);
        this.total = total;
    }
    private void setAverage(double average) {
        requireNonNull(average);
        this.average = average;
    }

    public String getCategory() {
        return category;
    }

    public String getChart() {
        return chart;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public ArrayList<String> getProperties() {
        return properties;
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    public double getTotal() {
        return total;
    }

    public double getAverage() {
        return average;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || (other instanceof Statistic
            && category.equals(((Statistic) other).category)
            && chart.equals(((Statistic) other).chart)
            && startDate.equals(((Statistic) other).startDate)
            && endDate.equals(((Statistic) other).endDate)
            && properties.equals(((Statistic) other).properties)
            && values.equals(((Statistic) other).values))
            && total == ((Statistic) other).total
            && average == ((Statistic) other).average;
    }
}
