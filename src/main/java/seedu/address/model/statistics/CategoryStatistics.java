package seedu.address.model.statistics;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.model.person.Category;

/**
 * Contains the list of statistics by category in the categorylist.
 */
public class CategoryStatistics {

    private final Category nameOfCategory;
    private double amountCalculated;

    public CategoryStatistics(Category nameOfCategory, double amountCalculated) {
        this.nameOfCategory = nameOfCategory;
        this.amountCalculated = amountCalculated;
    }

    public String getCategoryName() {
        return this.nameOfCategory.categoryName;
    }

    public void setAmountCalculated(double amountCalculated) {
        this.amountCalculated = amountCalculated;
    }

    public StringProperty getCategoryNameProperty() {
        return new SimpleStringProperty(nameOfCategory.categoryName);
    }

    public Category getCategory() {
        return this.nameOfCategory;
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
