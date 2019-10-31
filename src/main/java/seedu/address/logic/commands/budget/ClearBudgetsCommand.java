package seedu.address.logic.commands.budget;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenericCommandWord;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.Model;
import seedu.address.ui.budget.BudgetListPanel;

public class ClearBudgetsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.CLEAR + CommandGroup.BUDGET + "s";
    public static final String COMMAND_DESCRIPTION = "Clear all budgets";
    public static final String MESSAGE_SUCCESS = "All budgets (except Default Budget) in MooLah have been cleared!";

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
        model.clearBudgets();
        return new CommandResult(MESSAGE_SUCCESS, BudgetListPanel.PANEL_NAME);
    }
}
