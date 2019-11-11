package seedu.moolah.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.general.Timestamp;
import seedu.moolah.ui.budget.BudgetPanel;

/**
 * Switches budget window to a different period. Switching to future period is not allowed.
 */
public class SwitchPeriodCommand extends UndoableCommand {
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
    public static final String MESSAGE_PERIOD_IS_FUTURE = "You cannot switch to a period in the future.";

    private final Timestamp pastDate;

    /**
     * Creates an SwitchPeriodCommand to switch the budget window to a past period that contains the
     * specified {@code pastDate}.
     *
     * @param pastDate The date to anchor a past period.
     */
    public SwitchPeriodCommand(Timestamp pastDate) {
        requireNonNull(pastDate);
        this.pastDate = pastDate;
    }

    /**
     * Returns a description of this SwitchPeriodCommand.
     *
     * @return A string that describes this SwitchPeriodCommand.
     */
    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, pastDate);
    }


    /**
     * Validates this SwitchPeriodCommand with the current model, before execution.
     *
     * @param model The current model.
     * @throws CommandException If the period to switch to is a future period.
     */
    @Override
    protected void validate(Model model) throws CommandException {
        Budget currentPeriod = model.getPrimaryBudget().deepCopy().normalize(Timestamp.getCurrentTimestamp());

        if (pastDate.dateIsAfter(currentPeriod.getWindowEndDate())) {
            throw new CommandException(MESSAGE_PERIOD_IS_FUTURE);
        }
    }

    /**
     * Executes this SwitchPeriodCommand with the current model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A CommandResult consisting of success message and panel change request.
     */
    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.changePrimaryBudgetWindow(pastDate);
        return new CommandResult(
                String.format(MESSAGE_SWITCH_PERIOD_SUCCESS, pastDate),
                BudgetPanel.PANEL_NAME);
    }

    /**
     * Checks whether another object is identical to this SwitchPeriodCommand.
     *
     * @param other The other object to be compared.
     * @return True if the other object is a SwitchPeriodCommand with the same anchor date, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchPeriodCommand // instanceof handles nulls
                && pastDate.equals(((SwitchPeriodCommand) other).pastDate)); // state check
    }
}
