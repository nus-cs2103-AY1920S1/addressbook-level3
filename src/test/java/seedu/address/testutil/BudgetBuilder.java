package seedu.address.testutil;

import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;

import seedu.address.model.ExpenseList;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Name;

/**
 * A utility class to help with building Budget objects.
 */
public class BudgetBuilder {

    public static final String DEFAULT_NAME = "Korea holiday";
    public static final String DEFAULT_AMOUNT = "$2500";
    public static final String DEFAULT_AMOUNT_LEFT = "$2500";
    public static final String DEFAULT_START_DATE = "13/10/2019";
    public static final String DEFAULT_END_DATE = "25/10/2019";
    public static final ExpenseList DEFAULT_EXPENSE_LIST = getTypicalExpenseList();

    private Name name;
    private Amount amount;
    private Amount amountLeft;
    private Date startDate;
    private Date endDate;
    private ExpenseList expenseList;

    public BudgetBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        amountLeft = new Amount(DEFAULT_AMOUNT_LEFT);
        startDate = new Date(DEFAULT_START_DATE);
        endDate = new Date(DEFAULT_END_DATE);
        expenseList = new ExpenseList(DEFAULT_EXPENSE_LIST);
    }

    /**
     * Initializes the BudgetBuilder with the data of {@code budgetToCopy}.
     */
    public BudgetBuilder(Budget budgetToCopy) {
        name = budgetToCopy.getName();
        amount = budgetToCopy.getAmount();
        amountLeft = budgetToCopy.getAmountLeft();
        startDate = budgetToCopy.getStartDate();
        endDate = budgetToCopy.getEndDate();
        expenseList = budgetToCopy.getExpenseList();
    }

    /**
     * Sets the {@code Name} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code startDate} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withStartDate(String date) {
        this.startDate = new Date(date);
        return this;
    }

    /**
     * Sets the {@code endDate} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withEndDate(String date) {
        this.endDate = new Date(date);
        return this;
    }

    public Budget build() {
        return new Budget(name, amount, amount, startDate, endDate, expenseList);
    }
}
