package seedu.ichifund.logic.tasks.budget;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.ichifund.logic.tasks.Task;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.budget.ComputedBudget;
import seedu.ichifund.model.transaction.Transaction;

/**
 * Compute all the budget spending.
 */
public class ComputeBudgetTask extends Task {

    @Override
    public void execute(Model model) {
        ObservableList<Budget> budgetList = model.getFundBook().getBudgetList();
        for (Budget budget : budgetList) {
            List<Amount> filteredAmounts = model.getFundBook().getTransactionList()
                    .filtered(createPredicateForBudget(budget))
                    .stream()
                    .map(Transaction::getAmount)
                    .collect(Collectors.toList());
            Amount spending = Amount.addAll(filteredAmounts);
            model.setBudget(budget, new ComputedBudget(budget, spending));
        }
    }

    /***
     * Create a predicate according to the criteria set within the budget.
     *
     * @param budget The budget that will serve as a context to create the predicate.
     * @return A predicate according to the budget criteria.
     */
    private Predicate<Transaction> createPredicateForBudget(Budget budget) {
        Predicate<Transaction> predicate = transaction -> true;
        if (budget.getCategory() != null) {
            predicate = predicate.and(transaction -> transaction.isIn(budget.getCategory()));
        }
        if (budget.getMonth() != null && budget.getYear() != null) {
            predicate = predicate.and(transaction -> transaction.isIn(budget.getMonth()));
            predicate = predicate.and(transaction -> transaction.isIn(budget.getYear()));
        }
        return predicate;
    }

}
