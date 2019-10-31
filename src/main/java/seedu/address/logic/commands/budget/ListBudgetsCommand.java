package seedu.address.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUDGETS;

import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenericCommandWord;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.Model;
import seedu.address.ui.budget.BudgetListPanel;

/**
 * Lists all budgets in the MooLah to the user.
 */
public class ListBudgetsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.LIST + CommandGroup.BUDGET + "s";
    public static final String COMMAND_DESCRIPTION = "List budgets";
    public static final String MESSAGE_SUCCESS = "Listed all budgets";

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    protected void validate(Model model) {
        // No validation necessary.
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
        return new CommandResult(MESSAGE_SUCCESS, BudgetListPanel.PANEL_NAME);
    }
}
