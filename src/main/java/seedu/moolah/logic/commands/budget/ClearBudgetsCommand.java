package seedu.moolah.logic.commands.budget;

import static java.util.Objects.requireNonNull;

import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.model.Model;
import seedu.moolah.ui.budget.BudgetListPanel;

/**
 * Clears all budgets in MooLah.
 */
public class ClearBudgetsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.CLEAR + CommandGroup.BUDGET;
    public static final String COMMAND_DESCRIPTION = "Clear all budgets";
    public static final String MESSAGE_SUCCESS = "All budgets (except Default Budget) in MooLah have been cleared!";

    /**
     * Returns a description of this ClearBudgetsCommand.
     *
     * @return A string that describes this ClearBudgetsCommand.
     */
    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

    /**
     * Validates this ClearBudgetsCommand with the current model, before execution.
     *
     * @param model The current model.
     */
    @Override
    protected void validate(Model model) {
        // No validation necessary.
    }

    /**
     * Executes this ClearBudgetsCommand with the current model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A CommandResult consisting of success message and panel change request.
     */
    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearBudgets();
        return new CommandResult(MESSAGE_SUCCESS, BudgetListPanel.PANEL_NAME);
    }
}
