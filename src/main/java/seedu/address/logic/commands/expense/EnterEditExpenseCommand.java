package seedu.address.logic.commands.expense;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.expense.edit.EditExpenseFieldCommand;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expense.Expense;

/**
 * Command that enters the expense setup page when an existing expense is edited.
 */
public class EnterEditExpenseCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the expense information editing screen\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_ENTER_EDIT_EXPENSE_SUCCESS = " Welcome to your expense! %1$s";

    private final Index indexToEdit;

    public EnterEditExpenseCommand(Index indexToEdit) {
        this.indexToEdit = indexToEdit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Assumes EnterDayCommand has already been called
        List<Expense> lastShownList = model.getPageStatus().getTrip().getExpenseList().internalList;

        if (indexToEdit.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Expense expenseToEdit = lastShownList.get(indexToEdit.getZeroBased());
        EditExpenseFieldCommand.EditExpenseDescriptor editExpenseDescriptor =
                new EditExpenseFieldCommand.EditExpenseDescriptor(expenseToEdit);

        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.ADD_EXPENSE)
                .withNewExpense(expenseToEdit)
                .withNewEditExpenseDescriptor(editExpenseDescriptor));

        return new CommandResult(String.format(MESSAGE_ENTER_EDIT_EXPENSE_SUCCESS, expenseToEdit),
                true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterEditExpenseCommand;
    }
}
