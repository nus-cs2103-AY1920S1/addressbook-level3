package seedu.ichifund.model.analytics;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;

/**
 * Represents a report for expenditure breakdown by category, with the ability to be generated.
 */
public class BreakdownReport extends Report {

    private final Month month;
    private final Year year;
    private List<Data> breakdown;

    /**
     * Constructs a {@code BreakdownReport}.
     *
     * @param year A year.
     */
    public BreakdownReport(Month month, Year year) {
        requireNonNull(month);
        requireNonNull(year);
        this.month = month;
        this.year = year;
    }

    /**
     * Fills the {@code BreakdownReport}.
     *
     * @param breakdownList Trends to fill the report.
     */
    public void fillReport(List<Data> breakdownList) {
        requireNonNull(breakdownList);
        this.breakdown = breakdownList;
    }

    /**
     * Retrieves the month.
     */
    public Month getMonth() {
        return month;
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
    public List<Data> getBreakdownList() {
        return breakdown;
    }

    /**
     * Returns true if both trend reports contain the same month, year and data.
     * This defines a stronger notion of equality between two breakdown reports.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BreakdownReport)) {
            return false;
        }

        BreakdownReport otherReport = (BreakdownReport) other;
        return otherReport.getMonth().equals(getMonth())
                && otherReport.getYear().equals(getYear())
                && otherReport.getBreakdownList().equals(getBreakdownList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year, breakdown);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Month: ")
                .append(getYear());

        builder.append(" Year: ")
                .append(getYear());

        for (Data data : breakdown) {
            builder.append(data);
        }

        return builder.toString();
    }
}
