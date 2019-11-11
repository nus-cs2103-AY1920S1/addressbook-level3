package seedu.moolah.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.ui.budget.BudgetPanel;

/**
 * Adds a budget to MooLah.
 */
public class AddBudgetCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.ADD + CommandGroup.BUDGET;
    public static final String COMMAND_DESCRIPTION = "Add budget %1$s (%2$s)";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an budget to MooLah.\n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_PRICE + "AMOUNT "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_PERIOD + "PERIOD"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "school related expenses "
            + PREFIX_PRICE + "300 "
            + PREFIX_START_DATE + "23-06 "
            + PREFIX_PERIOD + "month";

    public static final String MESSAGE_SUCCESS = "New budget added:\n %1$s";
    public static final String MESSAGE_DUPLICATE_BUDGET = "This budget already exists in MooLah";

    private final Budget toAdd;

    /**
     * Creates an AddBudgetCommand to add the specified {@code Budget}
     *
     * @param budget The budget to be added to MooLah.
     */
    public AddBudgetCommand(Budget budget) {
        requireNonNull(budget);
        toAdd = budget;
    }

    /**
     * Returns a description of this AddBudgetCommand.
     *
     * @return A string that describes this AddBudgetCommand.
     */
    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, toAdd.getDescription(), toAdd.getAmount());
    }

    /**
     * Validates this AddBudgetCommand with the current model, before execution.
     *
     * @param model The current model.
     * @throws CommandException If there already exists an identical budget in model.
     */
    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBudget(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUDGET);
        }
    }

    /**
     * Executes this AddBudgetCommand with the current model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A CommandResult consisting of success message and panel change request.
     */
    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.addBudget(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), BudgetPanel.PANEL_NAME);
    }

    /**
     * Checks whether another object is identical to this AddBudgetCommand.
     *
     * @param other The other object to be compared.
     * @return True if the other object is a AddBudgetCommand with the same budget to add, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBudgetCommand // instanceof handles nulls
                && toAdd.equals(((AddBudgetCommand) other).toAdd));
    }
}
