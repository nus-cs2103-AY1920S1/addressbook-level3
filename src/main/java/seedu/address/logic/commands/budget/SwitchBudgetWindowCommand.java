package seedu.address.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.Model;
import seedu.address.model.expense.Timestamp;
import seedu.address.ui.budget.BudgetPanel;

/**
 * Switches budget window to a period in the past.
 */
public class SwitchBudgetWindowCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "switchperiod";
    public static final String COMMAND_DESCRIPTION = "Switch budget to period %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches the budget to a past period anchored by "
            + "the specified date and displays a list of expenses under this budget during that period.\n"
            + "Parameters: "
            + PREFIX_TIMESTAMP + "DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TIMESTAMP + "05-01-2019";

    public static final String MESSAGE_SWITCH_PERIOD_SUCCESS = "Budget window switched back to the period "
            + "anchored by: %1$s";

    private final Timestamp pastDate;

    public SwitchBudgetWindowCommand(Timestamp pastDate) {
        this.pastDate = pastDate;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, pastDate);
    }

    @Override
    protected void validate(Model model) {
        // No validation necessary.
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.changePrimaryBudgetWindow(pastDate);
        return new CommandResult(
                String.format(MESSAGE_SWITCH_PERIOD_SUCCESS, pastDate),
                BudgetPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchBudgetWindowCommand // instanceof handles nulls
                && pastDate.equals(((SwitchBudgetWindowCommand) other).pastDate)); // state check
    }
}
