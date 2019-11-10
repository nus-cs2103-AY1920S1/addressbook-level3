package seedu.moolah.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import seedu.moolah.model.expense.Timestamp;

/**
 * Represents the current budget window, with start and end dates, and a period.
 */
public class BudgetWindow {
    private Timestamp startDate;
    private Timestamp endDate;
    private final BudgetPeriod period;

    public BudgetWindow(Timestamp startDate, BudgetPeriod period) {
        requireAllNonNull(startDate, period);
        this.startDate = startDate.toStartOfDay();
        this.period = period;
        this.endDate = calculateEndDate();
    }

    public Timestamp getStartDate() {
        return this.startDate;
    }

    public Timestamp getEndDate() {
        return this.endDate;
    }

    public BudgetPeriod getBudgetPeriod() {
        return this.period;
    }

    /**
     * Checks if this budget window contains the specified timestamp. That is, the timestamp
     * is between start date and end date, inclusive.
     *
     * @param timestamp The specified timestamp to check against this window.
     * @return True if the window contains the timestamp, false otherwise.
     */
    public boolean contains(Timestamp timestamp) {
        requireNonNull(timestamp);
        return isAfterOrEqualStartDate(timestamp)
                && isBeforeOrEqualEndDate(timestamp);
    }

    private boolean isAfterOrEqualStartDate(Timestamp timestamp) {
        requireNonNull(timestamp);
        return timestamp.dateIsAfter(startDate.minusDays(1));
    }

    private boolean isBeforeOrEqualEndDate(Timestamp timestamp) {
        requireNonNull(timestamp);
        return timestamp.dateIsBefore(endDate.plusDays(1));
    }

    /**
     * Normalizes this window to a period containing the specified timestamp.
     *
     * @param rawAnchor The timestamp to anchor the period.
     */
    public void normalize(Timestamp rawAnchor) {
        requireNonNull(rawAnchor);
        LocalDateTime anchor = rawAnchor.toStartOfDay().fullTimestamp;
        LocalDateTime normalized;

        switch(period) {
        case YEAR:
            int currentDayOfYear = startDate.getDayOfYear();
            int anchorDayOfYear = anchor.getDayOfYear();
            normalized = (anchorDayOfYear >= currentDayOfYear)
                    ? anchor.withMonth(startDate.getMonthValue())
                    .withDayOfMonth(startDate.getDayOfMonth())
                    : anchor.minusYears(1)
                    .withMonth(startDate.getMonthValue())
                    .withDayOfMonth(startDate.getDayOfMonth());
            break;
        case MONTH:
            int currentDayOfMonth = startDate.getDayOfMonth();
            int anchorDayOfMonth = anchor.getDayOfMonth();
            normalized = (anchorDayOfMonth >= currentDayOfMonth)
                    ? anchor.withDayOfMonth(currentDayOfMonth)
                    : anchor.minusMonths(1).withDayOfMonth(currentDayOfMonth);
            break;
        case WEEK:
            long daysDiff = ChronoUnit.DAYS.between(startDate.getDate(), anchor.toLocalDate());
            long offset = daysDiff % 7 >= 0 ? daysDiff % 7 : daysDiff % 7 + 7;
            normalized = anchor.minusDays(offset);
            break;
        default:
            normalized = anchor;
        }
        startDate = new Timestamp(normalized);
        endDate = calculateEndDate();
    }

    /**
     * Calculates proper end date based on given start date and period.
     */
    private Timestamp calculateEndDate() {
        Timestamp endDate = startDate.plus(period.getPeriod());
        if ((period == BudgetPeriod.MONTH || period == BudgetPeriod.YEAR)
                && (endDate.getDayOfMonth() < startDate.getDayOfMonth())) {
            endDate = endDate.plusDays(1);
        }
        return endDate.minusDays(1).toEndOfDay();
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, period);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BudgetWindow)) {
            return false;
        }

        BudgetWindow otherWindow = (BudgetWindow) other;
        return otherWindow.startDate.getDate().equals(startDate.getDate())
                && otherWindow.period.equals(period)
                && otherWindow.endDate.getDate().equals(endDate.getDate());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Period: ")
                .append(period)
                .append(" Start date: ")
                .append(startDate)
                .append(" End date: ")
                .append(endDate);
        return builder.toString();
    }
}
