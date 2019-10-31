package seedu.ichifund.model.analytics;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.ichifund.model.date.Year;

/**
 * Represents a report for expenditure, income or balance trends, with the ability to be generated.
 */
public class TrendReport extends Report {

    private final Year year;
    private List<Data> trend;

    /**
     * Constructs a {@code TrendReport}.
     *
     * @param year A year.
     */
    public TrendReport(Year year) {
        requireNonNull(year);
        this.year = year;
    }

    /**
     * Fills the {@code TrendReport}.
     *
     * @param trendList Trends to fill the report.
     */
    public void fillReport(List<Data> trendList) {
        requireNonNull(trendList);
        this.trend = trendList;
    }

    /**
     * Retrieves the year.
     */
    public Year getYear() {
        return year;
    }

    /**
     * Retrieves the trend list.
     */
    public List<Data> getTrendList() {
        return trend;
    }

    /**
     * Retrieves the sorted trend list.
     */
    public List<Data> getSortedTrendList() {
        List<Data> sortedTrendList = new ArrayList<>(trend);
        Collections.sort(sortedTrendList);
        return sortedTrendList;
    }

    /**
     * Returns true if both trend reports contain the same year and data.
     * This defines a stronger notion of equality between two trend reports.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TrendReport)) {
            return false;
        }

        TrendReport otherReport = (TrendReport) other;
        return otherReport.getYear().equals(getYear())
                && otherReport.getTrendList().equals(getTrendList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, trend);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Year: ")
                .append(getYear());

        for (Data data : trend) {
            builder.append(data);
        }

        return builder.toString();
    }
}
