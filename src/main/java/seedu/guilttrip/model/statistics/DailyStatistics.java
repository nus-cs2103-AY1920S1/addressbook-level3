package seedu.guilttrip.model.statistics;

import java.time.LocalDate;
import java.time.YearMonth;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Contains the list of statistics for the specified day.
 */
public class DailyStatistics {

    private final LocalDate EXACT_DAY;
    private double totalExpense;
    private double totalIncome;

    public DailyStatistics(LocalDate day, double totalExpense, double totalIncome) {
        this.EXACT_DAY = day;
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
    }

    public int getDate() {
        return EXACT_DAY.getDayOfMonth();
    }

    public IntegerProperty getMonth() {
        return new SimpleIntegerProperty(EXACT_DAY.getMonth().getValue());
    }

    public LocalDate getExactDay() {
        return this.EXACT_DAY;
    }

    public int getNumberOfDays() {
        YearMonth yearMonth = YearMonth.of(this.EXACT_DAY.getYear(), this.EXACT_DAY.getMonthValue());
        return yearMonth.lengthOfMonth();
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return (other instanceof DailyStatistics // instanceof handles nulls
                && ((DailyStatistics) other).getExactDay().equals(this.getExactDay())
                && this.totalExpense == (((DailyStatistics) other).getTotalExpense())
                && this.totalIncome == (((DailyStatistics) other).getTotalIncome()));
    }
}
