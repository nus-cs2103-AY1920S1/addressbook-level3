package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Expense}'s {@code Category} matches the {@code Budget}'s {@code Category}.
 */
public class ExpenseMatchesBudgetPredicate implements Predicate<Expense> {
    private final Category cat;
    private final Date startDate;
    private final Date endDate;


    public ExpenseMatchesBudgetPredicate(Category cat, Date startDate, Date endDate) {
        this.cat = cat;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean test(Expense expense) {
        return cat.equals(expense.getCategory())
                && (startDate.isBefore(expense.getDate()) || startDate.isEqual(expense.getDate()))
                && (expense.getDate().isBefore(endDate) || expense.getDate().isEqual(endDate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseMatchesBudgetPredicate // instanceof handles nulls
                && cat.equals(((ExpenseMatchesBudgetPredicate) other).cat) // state check
                && startDate.equals(((ExpenseMatchesBudgetPredicate) other).startDate) // state check
                && endDate.equals(((ExpenseMatchesBudgetPredicate) other).endDate)); // state check
    }

}

