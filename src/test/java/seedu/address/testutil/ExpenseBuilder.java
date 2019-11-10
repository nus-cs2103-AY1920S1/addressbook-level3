package seedu.address.testutil;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.expense.DayNumber;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.MiscExpense;
import seedu.address.model.expense.PlannedExpense;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 * Builder class to accommodate optional properties using builder pattern.
 * Can be used to construct {@link Expense} without optional fields.
 */
public class ExpenseBuilder {
    private Name name;
    private Budget budget;
    private DayNumber dayNumber;
    private String type;

    private ExpenseBuilder() {}

    public static ExpenseBuilder newInstance() {
        return new ExpenseBuilder();
    }

    /**
     * Constructs a ExpenseBuilder instance from the specified expense.
     *
     * @param expense Expense to use.
     * @return new ExpenseBuilder instance.
     */
    public static ExpenseBuilder of(Expense expense) {
        requireAllNonNull(expense.getName(), expense.getBudget());
        if (expense instanceof PlannedExpense) {
            return ExpenseBuilder.newInstance()
                    .setName(expense.getName())
                    .setBudget(expense.getBudget())
                    .setDayNumber(expense.getDayNumber())
                    .setType("planned");
        } else if (expense instanceof MiscExpense) {
            return ExpenseBuilder.newInstance()
                    .setName(expense.getName())
                    .setBudget(expense.getBudget())
                    .setDayNumber(expense.getDayNumber())
                    .setType("misc");
        } else {
            throw new AssertionError("Invalid expense type");
        }
    }

    public ExpenseBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public ExpenseBuilder setBudget(Budget budget) {
        this.budget = budget;
        return this;
    }

    public ExpenseBuilder setDayNumber(DayNumber dayNumber) {
        this.dayNumber = dayNumber;
        return this;
    }

    public ExpenseBuilder setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Terminal method to construct new {@link Expense}.
     */
    public Expense build() {
        if (type.equals("planned")) {
            return new PlannedExpense(name, budget, dayNumber);
        } else if (type.equals("misc")) {
            return new MiscExpense(name, budget, dayNumber);
        } else {
            throw new AssertionError("Invalid expense type");
        }
    }

}
