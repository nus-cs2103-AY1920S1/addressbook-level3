package seedu.address.logic.commands.expense;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;

/**
 * Command that sort the expenses.
 */
public class SortExpensesCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort expenses in expense manager.\n"
            + "Parameters: property (name or amount)";

    public static final String MESSAGE_SORT_EXPENSE_FAILURE = "Failed to sort your expenses, expenses can only be"
            + " sorted by name or amount";
    public static final String MESSAGE_SORT_EXPENSE_SUCCESS = "Your expenses are sorted by %s";

    private final String property;

    public SortExpensesCommand(String property) {
        this.property = property;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (property.equals("name")) {
            model.getPageStatus().getTrip().getExpenseList().sort(property);

        } else if (property.equals("amount")) {
            model.getPageStatus().getTrip().getExpenseList().sort(property);
        } else {
            throw new CommandException(MESSAGE_SORT_EXPENSE_FAILURE);
        }

        model.setPageStatus(model.getPageStatus()
                .withResetEditEventDescriptor()
                .withNewPageType(PageType.EXPENSE_MANAGER));

        return new CommandResult(String.format(MESSAGE_SORT_EXPENSE_SUCCESS, property),
                true, COMMAND_WORD);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof SortExpensesCommand;
    }

}
