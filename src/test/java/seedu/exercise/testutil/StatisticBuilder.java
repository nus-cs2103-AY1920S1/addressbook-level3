package seedu.exercise.testutil;

import java.util.ArrayList;

import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.model.property.Date;

/**
 * A utility class to help with building Statistic objects.
 */
public class StatisticBuilder {

    private static final String DEFAULT_CATEGORY = "calories";
    private static final String DEFAULT_CHART = "linechart";
    private static final String DEFAULT_START_DATE = "17/10/2019";
    private static final String DEFAULT_END_DATE = "23/10/2019";

    private String category;
    private String chart;
    private Date startDate;
    private Date endDate;
    private ArrayList<String> properties;
    private ArrayList<Double> values;

    public StatisticBuilder() {
        this.category = DEFAULT_CATEGORY;
        this.chart = DEFAULT_CHART;
        this.startDate = new Date(DEFAULT_START_DATE);
        this.endDate = new Date(DEFAULT_END_DATE);
        this.properties = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    /**
     * Sets the category of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * Sets the chart of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withChart(String chart) {
        this.chart = chart;
        return this;
    }

    /**
     * Sets the start date of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Sets the end date of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * Sets the properties of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withProperties(ArrayList<String> properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Sets the values of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withValues(ArrayList<Double> values) {
        this.values = values;
        return this;
    }

    public Statistic build() {
        return new Statistic(category, chart, startDate, endDate, properties, values);
    }
}
