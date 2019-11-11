package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUDGETS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ViewState;

/**
 * Lists all expenses in the Budget to the user.
 */
public class ListBudgetsCommand extends Command {

    public static final String COMMAND_WORD = "listBudgets";

    public static final String MESSAGE_SUCCESS = "Listed all budgets";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
        model.setViewState(ViewState.BUDGETLIST);
        return new CommandResult(null, model.getFilteredBudgetList(), null, MESSAGE_SUCCESS);
    }
}
