package seedu.guilttrip.model.entry;

import java.util.function.Predicate;

/**
 * Tests that a {@code Income}'s {@code Category} matches the {@code Budget}'s {@code Category}.
 */
public class IncomeMatchesBudgetPredicate implements Predicate<Income> {
    private final Category cat;
    private final Date startDate;
    private final Date endDate;


    public IncomeMatchesBudgetPredicate(Category cat, Date startDate, Date endDate) {
        this.cat = cat;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean test(Income income) {
        return cat.equals(income.getCategory())
                && (startDate.isBefore(income.getDate()) || startDate.isEqual(income.getDate()))
                && income.getDate().isBefore(endDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IncomeMatchesBudgetPredicate // instanceof handles nulls
                && cat.equals(((IncomeMatchesBudgetPredicate) other).cat) // state check
                && startDate.equals(((IncomeMatchesBudgetPredicate) other).startDate) // state check
                && endDate.equals(((IncomeMatchesBudgetPredicate) other).endDate)); // state check
    }

}

