package seedu.billboard.testutil;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.expense.Expense;

/**
 * A utility class to help with building Billboard objects.
 * Example usage: <br>
 *     {@code Billboard ab = new BillboardBuilder().withExpense("John", "Doe").build();}
 */
public class BillboardBuilder {

    private Billboard billboard;

    public BillboardBuilder() {
        billboard = new Billboard();
    }

    public BillboardBuilder(Billboard billboard) {
        this.billboard = billboard;
    }

    /**
     * Adds a new {@code Expense} to the {@code Billboard} that we are building.
     */
    public BillboardBuilder withExpense(Expense expense) {
        billboard.addExpense(expense);
        return this;
    }

    public Billboard build() {
        return billboard;
    }
}
