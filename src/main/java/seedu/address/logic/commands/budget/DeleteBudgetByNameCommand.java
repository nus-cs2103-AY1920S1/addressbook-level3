package seedu.address.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expense.Description;
import seedu.address.ui.budget.BudgetPanel;

public class DeleteBudgetByNameCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete" + CommandGroup.BUDGET;
    public static final String COMMAND_DESCRIPTION = "Delete budget with name %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the budget identified by the name.\n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Holiday";

    public static final String MESSAGE_DELETE_BUDGET_SUCCESS = "Deleted Budget: %1$s";
    public static final String MESSAGE_BUDGET_NOT_FOUND = "This budget does not exist in MooLah";

    private final Description description;

    /**
     * Creates an DeleteBudgetByNameCommand to delete the budget with the specified {@code description}.
     */
    public DeleteBudgetByNameCommand(Description description) {
        requireNonNull(description);
        this.description = description;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, description);
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasBudgetWithName(description)) {
            throw new CommandException(MESSAGE_BUDGET_NOT_FOUND);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.deleteBudgetWithName(description);
        return new CommandResult(String.format(MESSAGE_DELETE_BUDGET_SUCCESS, description),
                BudgetPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBudgetByNameCommand // instanceof handles nulls
                && description.equals(((DeleteBudgetByNameCommand) other).description));
    }
}
