package seedu.address.testutil;

import seedu.address.model.ExpenseList;
import seedu.address.model.expense.Expense;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ExpenseList ab = new AddressBookBuilder().withExpense("John", "Doe").build();}
 */
public class ExpenseListBuilder {

    private ExpenseList expenseList;

    public ExpenseListBuilder() {
        expenseList = new ExpenseList();
    }

    public ExpenseListBuilder(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }

    /**
     * Adds a new {@code Expense} to the {@code ExpenseList} that we are building.
     */
    public ExpenseListBuilder withExpense(Expense expense) {
        expenseList.addExpense(expense);
        return this;
    }

    public ExpenseList build() {
        return expenseList;
    }
}
