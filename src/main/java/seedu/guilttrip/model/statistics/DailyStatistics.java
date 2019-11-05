package seedu.guilttrip.model.statistics;

import java.time.LocalDate;
import java.time.YearMonth;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Contains the list of statistics for the specified day.
 */
public class DailyStatistics {

    private final LocalDate exactDay;
    private double totalExpense;
    private double totalIncome;

    public DailyStatistics(LocalDate day, double totalExpense, double totalIncome) {
        this.exactDay = day;
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
    }

    public int getDate() {
        return exactDay.getDayOfMonth();
    }

    public IntegerProperty getMonth() {
        return new SimpleIntegerProperty(exactDay.getMonth().getValue());
    }

    public LocalDate getExactDay() {
        return this.exactDay;
    }

    public int getNumberOfDays() {
        YearMonth yearMonth = YearMonth.of(this.exactDay.getYear(), this.exactDay.getMonthValue());
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
