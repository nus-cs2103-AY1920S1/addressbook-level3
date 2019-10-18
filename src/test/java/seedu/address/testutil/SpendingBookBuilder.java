package seedu.address.testutil;

import seedu.address.model.SpendingBook;
import seedu.address.model.budget.Budget;
import seedu.address.model.spending.Spending;

/**
 * A utility class to help with building SpendingBook objects.
 * Example usage: <br>
 *     {@code SpendingBook sb = new SpendingBookBuilder().withPerson("John", "Doe").build();}
 */
public class SpendingBookBuilder {

    private SpendingBook spendingBook;

    public SpendingBookBuilder() {
        spendingBook = new SpendingBook();
    }

    public SpendingBookBuilder(SpendingBook spendingBook) {
        this.spendingBook = spendingBook;
    }

    /**
     * Adds a new {@code Spending} to the {@code SpendingBook} that we are building.
     */
    public SpendingBookBuilder withSpending(Spending spending) {
        spendingBook.addSpending(spending);
        return this;
    }

    /**
     * sets a new {@code Budget} to the {@code SpendingBook} that we are building.
     */
    public SpendingBookBuilder withBudget(Budget budget) {
        spendingBook.setBudget(budget);
        return this;
    }

    public SpendingBook build() {
        return spendingBook;
    }
}
