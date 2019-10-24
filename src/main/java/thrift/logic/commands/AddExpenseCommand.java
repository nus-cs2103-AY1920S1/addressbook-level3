package thrift.logic.commands;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import thrift.logic.parser.CliSyntax;
import thrift.model.Model;
import thrift.model.transaction.Expense;
import thrift.ui.TransactionListPanel;

/**
 * Adds an expense transaction to the THRIFT.
 */
public class AddExpenseCommand extends ScrollingCommand implements Undoable {

    public static final String COMMAND_WORD = "add_expense";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Adds an expense transaction to THRIFT.\n"
            + "Format: "
            + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "NAME DESCRIPTION "
            + CliSyntax.PREFIX_VALUE + "VALUE "
            + "[" + CliSyntax.PREFIX_REMARK + "REMARK] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Possible usages of " + COMMAND_WORD + ": \n"
            + "To add an expense: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + "Laksa " + CliSyntax.PREFIX_VALUE + "4\n"
            + "To add an expense with remark: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + "Laksa " + CliSyntax.PREFIX_VALUE + "4 "
            + CliSyntax.PREFIX_REMARK + "One of my favourite\n"
            + "To add an expense with tag: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + "Laksa " + CliSyntax.PREFIX_VALUE + "4 "
            + CliSyntax.PREFIX_TAG + "Food\n"
            + "To add an expense with remark and tag: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + "Laksa " + CliSyntax.PREFIX_VALUE + "4 "
            + CliSyntax.PREFIX_REMARK + "One of my favourite " + CliSyntax.PREFIX_TAG + "Food";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense transaction to THRIFT.\n"
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME DESCRIPTION "
            + CliSyntax.PREFIX_VALUE + "VALUE "
            + "[" + CliSyntax.PREFIX_REMARK + "REMARK] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Laksa "
            + CliSyntax.PREFIX_VALUE + "4.50 "
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
    public CommandResult execute(Model model, TransactionListPanel transactionListPanel) {
        requireNonNull(model);
        model.addExpense(toAdd);

        // Use null comparison instead of requireNonNull(transactionListPanel) as current JUnit tests are unable to
        // handle JavaFX initialization
        if (model.isInView(toAdd) && transactionListPanel != null) {
            transactionListPanel.getTransactionListView().scrollTo(toAdd);
        }
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
        requireNonNull(model);
        model.deleteLastTransaction();
    }

    @Override
    public void redo(Model model) {
        requireAllNonNull(model, toAdd);
        model.addExpense(toAdd);
    }
}
