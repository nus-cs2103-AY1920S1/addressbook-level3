package seedu.guilttrip.commons.util;

import javafx.concurrent.Task;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.AutoExpense;

/**
 * A runnable task (since Task implements Runnable) that updates the guiltTrip with
 * Expenses generated from autoExpenses.
 */
public class AutoExpenseUpdater extends Task<Void> {
    private final Model model;

    /**
     * Constructor: takes in the model to update autoExpenses in.
     *
     * @param model
     */
    public AutoExpenseUpdater(Model model) {
        this.model = model;
    }

    /**
     * Generates Expenses from AutoExpenses and update the GuiltTrip.
     */
    private void createExpensesFromAutoExpenses() {
        boolean shouldCommit = !model.getFilteredAutoExpenses().stream().allMatch(AutoExpense::isUpToDate);
        for (AutoExpense autoExpense : model.getFilteredAutoExpenses()) {
            autoExpense.generateNewExpenses().stream().forEach(model::addExpense);
        }
        if (shouldCommit) {
            model.commitGuiltTrip();
        }
    }

    @Override
    protected Void call() throws Exception {
        createExpensesFromAutoExpenses();
        Thread.currentThread().interrupt();
        return null;
    }
}
