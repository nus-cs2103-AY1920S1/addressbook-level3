package seedu.moolah.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Description;
import seedu.moolah.ui.budget.BudgetPanel;

/**
 * Switches the primary budget to another budget.
 */
public class SwitchBudgetCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "switch" + CommandGroup.BUDGET;
    public static final String COMMAND_DESCRIPTION = "Switch budget to %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches the primary budget to another budget.\n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Outside School";

    public static final String MESSAGE_SUCCESS = "Primary budget switched to:\n %1$s";
    public static final String MESSAGE_BUDGET_NOT_FOUND = "This budget does not exist in MooLah";
    public static final String MESSAGE_BUDGET_ALREADY_PRIMARY = "This budget is already the primary budget";

    private final Description targetDescription;

    /**
     * Creates an SwitchBudgetCommand to switch primary budget the budget with the specified {@code targetDescription}.
     */
    public SwitchBudgetCommand(Description targetDescription) {
        requireNonNull(targetDescription);
        this.targetDescription = targetDescription;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, targetDescription);
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasBudgetWithName(targetDescription)) {
            throw new CommandException(MESSAGE_BUDGET_NOT_FOUND);
        }

        if (model.getPrimaryBudget().getDescription().equals(targetDescription)) {
            throw new CommandException(MESSAGE_BUDGET_ALREADY_PRIMARY);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.switchBudgetTo(targetDescription);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetDescription), BudgetPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchBudgetCommand // instanceof handles nulls
                && targetDescription.equals(((SwitchBudgetCommand) other).targetDescription));
    }

    private Budget createEditedBudget(Budget budgetToEdit, boolean editedIsPrimary) {
        return new Budget(budgetToEdit.getDescription(), budgetToEdit.getAmount(), budgetToEdit.getWindowStartDate(),
                budgetToEdit.getBudgetPeriod(), budgetToEdit.getExpenses(), editedIsPrimary);
    }
}
