package seedu.address.model.statistics;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.model.person.Category;

public class DailyStatistics {

    private final int statsThatIsCalculated;
    private double amountCalculated;

    public DailyStatistics(int day, double amountCalculated) {
        this.statsThatIsCalculated = day;
        this.amountCalculated = amountCalculated;
    }

    public IntegerProperty getDailyProperty() {
        return new SimpleIntegerProperty(statsThatIsCalculated);
    }

    public void setAmountCalculated(double amountCalculated) {
        this.amountCalculated = amountCalculated;
    }

    public int getDay() {
        return this.statsThatIsCalculated;
    }

    public double getAmountCalculated() {
        return amountCalculated;
    }

    public DoubleProperty getAmountCalculatedProperty() {
        return new SimpleDoubleProperty(this.amountCalculated);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return (other instanceof CategoryStatistics // instanceof handles nulls
                && ((CategoryStatistics) other).getCategory().equals(this.nameOfCategory)
                && this.amountCalculated == (((CategoryStatistics) other).getAmountCalculated()));
    }
}
