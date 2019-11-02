package seedu.address.model.statistics;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.model.person.Category;
import seedu.address.model.person.Date;

public class DailyStatistics {

    private final LocalDate exactDay;
    private double totalExpense;
    private double totalIncome;

    public DailyStatistics(LocalDate day, double totalExpense, double totalIncome) {
        this.exactDay = day;
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
    }

    public IntegerProperty getDate() {
        return new SimpleIntegerProperty(exactDay.getDayOfMonth());
    }

    public IntegerProperty getMonth() {
        return new SimpleIntegerProperty(exactDay.getMonth().getValue());
    }

    public LocalDate getExactDay() {
        return this.exactDay;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getTotalIncome() { return totalIncome; }

    public DoubleProperty getTotalExpenseForGraph() {
        return new SimpleDoubleProperty(this.totalExpense);
    }

    public DoubleProperty getTotalIncomeForGraph() {
        return new SimpleDoubleProperty(this.totalIncome);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return (other instanceof DailyStatistics // instanceof handles nulls
                && ((DailyStatistics) other).getExactDay().equals(this.getExactDay())
                && this.totalExpense == (((DailyStatistics) other).getTotalExpense())
                && this.totalIncome == (((DailyStatistics) other).totalIncome()));
    }
}
