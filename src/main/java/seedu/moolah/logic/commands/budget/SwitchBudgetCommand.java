package seedu.moolah.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.general.Description;
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
     *
     * @param targetDescription The description of the budget to switch to.
     */
    public SwitchBudgetCommand(Description targetDescription) {
        requireNonNull(targetDescription);
        this.targetDescription = targetDescription;
    }

    /**
     * Returns a description of this SwitchBudgetCommand.
     *
     * @return A string that describes this SwitchBudgetCommand.
     */
    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, targetDescription);
    }

    /**
     * Validates this SwitchBudgetCommand with the current model, before execution.
     *
     * @param model The current model.
     * @throws CommandException If the budget does not exist, or if the budget is already primary.
     */
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

    /**
     * Executes this SwitchBudgetCommand with the current model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A CommandResult consisting of success message and panel change request.
     */
    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.switchBudgetTo(targetDescription);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetDescription), BudgetPanel.PANEL_NAME);
    }

    /**
     * Checks whether another object is identical to this SwitchBudgetCommand.
     *
     * @param other The other object to be compared.
     * @return True if the other object is a SwitchBudgetCommand with the same target description, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchBudgetCommand // instanceof handles nulls
                && targetDescription.equals(((SwitchBudgetCommand) other).targetDescription));
    }
}
