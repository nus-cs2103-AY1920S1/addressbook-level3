package seedu.address.logic.commands.expense;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expense.Expense;

/**
 * Command that deletes an expense.
 */
public class DeleteExpenseCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an expense from expense manager.\n"
            + "Parameters: INDEX (a positive integer)";

    public static final String MESSAGE_DELETE_EXPENSE_FAILURE = "The expense you are trying to remove is likely to "
            + "be associated with an event, please go to the corresponding event to delete the expense\n"
            + "Command: goto INDEX (a positive integer)";
    public static final String MESSAGE_DELETE_EXPENSE_SUCCESS = "Deleted your expense : ";

    private final Index indexToDelete;

    public DeleteExpenseCommand(Index indexToDelete) {
        this.indexToDelete = indexToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Assumes enter trip has been called first
        List<Expense> lastShownList = model.getPageStatus().getTrip().getExpenseList().internalList;

        if (indexToDelete.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        // References preserved by PageStatus
        Expense expenseToDelete = lastShownList.get(indexToDelete.getZeroBased());
        try {
            model.getPageStatus().getTrip().getExpenseList().removeByUser(expenseToDelete);
        } catch (Exception ex) {
            return new CommandResult(MESSAGE_DELETE_EXPENSE_FAILURE);
        }

        model.setPageStatus(model.getPageStatus()
                .withResetEditEventDescriptor()
                .withNewPageType(PageType.EXPENSE_MANAGER));

        return new CommandResult(MESSAGE_DELETE_EXPENSE_SUCCESS
                + expenseToDelete.getName().toString(), true, COMMAND_WORD);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DeleteExpenseCommand;
    }

}
