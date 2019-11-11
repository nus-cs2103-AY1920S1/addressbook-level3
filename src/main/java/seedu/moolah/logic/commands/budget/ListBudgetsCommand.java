package seedu.moolah.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.model.Model.PREDICATE_SHOW_ALL_BUDGETS;

import seedu.moolah.logic.commands.Command;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.model.Model;
import seedu.moolah.ui.budget.BudgetListPanel;

/**
 * Lists all budgets in the MooLah to the user.
 */
public class ListBudgetsCommand extends Command {

    public static final String COMMAND_WORD = GenericCommandWord.LIST + CommandGroup.BUDGET;
    public static final String COMMAND_DESCRIPTION = "List budgets";
    public static final String MESSAGE_SUCCESS = "Listed all budgets";

    /**
     * Validates this ListBudgetsCommand with the current model, before execution.
     *
     * @param model The current model.
     */
    @Override
    protected void validate(Model model) {
        // No validation necessary.
    }

    /**
     * Executes this ListBudgetsCommand with the current model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A CommandResult consisting of success message and panel change request.
     */
    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
        return new CommandResult(MESSAGE_SUCCESS, BudgetListPanel.PANEL_NAME);
    }
}
