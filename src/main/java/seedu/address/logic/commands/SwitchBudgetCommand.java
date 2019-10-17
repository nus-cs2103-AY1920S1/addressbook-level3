package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;

/**
 * Switches the primary budget to another budget.
 */
public class SwitchBudgetCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches the primary budget to another budget. \n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION ";

    public static final String MESSAGE_SUCCESS = "Primary budget switched to: %1$s";
    public static final String MESSAGE_BUDGET_NOT_FOUND = "This budget does not exist in the address book";
    public static final String MESSAGE_BUDGET_ALREADY_PRIMARY = "This budget is already the primary budget";

    private final Budget target;

    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public SwitchBudgetCommand(Budget target) {
        requireNonNull(target);
        this.target = target;
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasBudgetWithSameName(target)) {
            throw new CommandException(MESSAGE_BUDGET_NOT_FOUND);
        }

        if (model.getPrimaryBudget().equals(target)) {
            throw new CommandException(MESSAGE_BUDGET_ALREADY_PRIMARY);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.switchBudgetTo(target);
        return new CommandResult(String.format(MESSAGE_SUCCESS, target));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchBudgetCommand // instanceof handles nulls
                && target.equals(((SwitchBudgetCommand) other).target));
    }
}
