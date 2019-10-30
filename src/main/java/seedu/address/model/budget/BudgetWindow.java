package seedu.address.model.budget;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.expense.Timestamp;

/**
 * Dummy.
 */
public class BudgetWindow {
    private Timestamp startDate;
    private Timestamp endDate;
    private final Period period;

    public BudgetWindow(Timestamp startDate, Period period) {
        this.startDate = startDate.toStartOfDay();
        this.period = period;
        this.endDate = calculateEndDate();
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public Period getPeriod() {
        return period;
    }

    /**
     * Dummy.
     * @param timestamp
     * @return
     */
    public boolean contains(Timestamp timestamp) {
        return isAfterOrEqualStartDate(timestamp)
                && isBeforeOrEqualEndDate(timestamp);
    }

    private boolean isAfterOrEqualStartDate(Timestamp timestamp) {
        return timestamp.dateIsAfter(startDate.minusDays(1));
    }

    private boolean isBeforeOrEqualEndDate(Timestamp timestamp) {
        return timestamp.dateIsBefore(endDate.plusDays(1));
    }

    /**
     * Dummy.
     * @param rawAnchor
     */
    public void normalize(Timestamp rawAnchor) {
        LocalDateTime anchor = rawAnchor.toStartOfDay().fullTimestamp;

        if (period.getMonths() == 1) {

            int specifiedDayOfMonth = startDate.getDayOfMonth();
            int currentDayOfMonth = anchor.getDayOfMonth();
            LocalDateTime normalized;
            if (currentDayOfMonth >= specifiedDayOfMonth) {
                normalized = anchor.withDayOfMonth(specifiedDayOfMonth);
            } else {
                normalized = anchor.minusMonths(1).withDayOfMonth(specifiedDayOfMonth);
            }
            startDate = new Timestamp(normalized);
            endDate = calculateEndDate();

        } else if (period.getYears() == 1) {

            int specifiedDayOfYear = startDate.getDayOfYear();
            int currentDayOfYear = anchor.getDayOfYear();
            LocalDateTime normalized;
            if (currentDayOfYear >= specifiedDayOfYear) {
                normalized = anchor.withMonth(startDate.getMonthValue())
                        .withDayOfMonth(startDate.getDayOfMonth());
            } else {
                normalized = anchor.minusYears(1)
                        .withMonth(startDate.getMonthValue())
                        .withDayOfMonth(startDate.getDayOfMonth());
            }
            startDate = new Timestamp(normalized);
            endDate = calculateEndDate();

        } else if (period.getDays() == 1) {

            startDate = new Timestamp(anchor);
            endDate = calculateEndDate();

        } else if (period.getDays() == 7) {

            long daysDiff = ChronoUnit.DAYS.between(startDate.getDate(), anchor.toLocalDate());
            long offset = daysDiff % 7;
            startDate = new Timestamp(anchor.toLocalDate().minusDays(offset).atStartOfDay());
            endDate = calculateEndDate();

        }
    }

    /**
     * Calculates proper end date based on given start date and period.
     */
    private Timestamp calculateEndDate() {
        Timestamp endDate = startDate.plus(period);
        if (period.getMonths() == 1 && endDate.getDayOfMonth() < startDate.getDayOfMonth()) {
            endDate.plusDays(1);
        }
        return endDate.minusDays(1).toEndOfDay();
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
                .append(ParserUtil.formatPeriod(period))
                .append(" Start date: ")
                .append(startDate)
                .append(" End date: ")
                .append(endDate);
        return builder.toString();
    }


}
