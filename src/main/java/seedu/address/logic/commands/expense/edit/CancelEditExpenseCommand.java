package seedu.address.logic.commands.expense.edit;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expense.Expense;

/**
 * Command that cancels editing the expense, bringing the user back to the expenses manager screen.
 */
public class CancelEditExpenseCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels editing or creating a new expense.";

    public static final String MESSAGE_CANCEL_CREATE_SUCCESS = "Cancelled creating the expense!";
    public static final String MESSAGE_CANCEL_EDIT_SUCCESS = "Cancelled editing the expense: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Expense currentlyEditingExpense = model.getPageStatus().getExpense();
        model.setPageStatus(model.getPageStatus()
                .withResetEditExpenseDescriptor()
                .withNewPageType(PageType.EXPENSE_MANAGER)
                .withResetExpense());

        if (currentlyEditingExpense == null) {
            return new CommandResult(MESSAGE_CANCEL_CREATE_SUCCESS, true);
        } else {
            return new CommandResult(
                    String.format(MESSAGE_CANCEL_EDIT_SUCCESS, currentlyEditingExpense), true);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CancelEditExpenseCommand;
    }
}
