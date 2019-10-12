package thrift.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import thrift.logic.parser.CliSyntax;
import thrift.model.Model;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Transaction;

/**
 * Adds an expense transaction to the THRIFT.
 */
public class AddExpenseCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "add_expense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense transaction to THRIFT. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME DESCRIPTION "
            + CliSyntax.PREFIX_COST + "COST "
            + "[" + CliSyntax.PREFIX_REMARK + "REMARK] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Laksa "
            + CliSyntax.PREFIX_COST + "4.50 "
            + CliSyntax.PREFIX_REMARK + "Ate at Changi Village "
            + CliSyntax.PREFIX_TAG + "Lunch "
            + CliSyntax.PREFIX_TAG + "Meal ";

    public static final String MESSAGE_SUCCESS = "New expense added: %1$s";

    private final Expense toAdd;

    /**
     * Creates an AddExpenseCommand to add the specified {@code Expense}
     */
    public AddExpenseCommand(Expense expense) {
        requireNonNull(expense);
        toAdd = expense;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.addExpense(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExpenseCommand // instanceof handles nulls
                && toAdd.equals(((AddExpenseCommand) other).toAdd));
    }

    @Override
    public void undo(Model model) {
        List<Transaction> lastShownList = model.getThrift().getTransactionList();
        assert lastShownList.size() > 0 : "No transactions in the list";
        Transaction transactionToDelete = lastShownList.get(lastShownList.size() - 1);
        model.deleteTransaction(transactionToDelete);
    }
}
