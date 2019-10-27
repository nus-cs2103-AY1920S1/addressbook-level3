package thrift.logic.commands;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import thrift.logic.parser.CliSyntax;
import thrift.model.Model;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;
import thrift.ui.TransactionListPanel;

/**
 * Adds an expense transaction to the THRIFT.
 */
public class AddIncomeCommand extends ScrollingCommand implements Undoable {

    public static final String COMMAND_WORD = "add_income";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Adds an income transaction to THRIFT.\n"
            + "Format: "
            + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "NAME DESCRIPTION "
            + CliSyntax.PREFIX_VALUE + "VALUE "
            + "[" + CliSyntax.PREFIX_REMARK + "REMARK] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Possible usages of " + COMMAND_WORD + ": \n"
            + "To add an income: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + "Bursary " + CliSyntax.PREFIX_VALUE + "1000\n"
            + "To add an income with remark: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + "Bursary " + CliSyntax.PREFIX_VALUE + "1000 "
            + CliSyntax.PREFIX_REMARK + "From SDCC\n"
            + "To add an income with tags: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + "Bursary " + CliSyntax.PREFIX_VALUE + "1000 "
            + CliSyntax.PREFIX_TAG + "SDCC " + CliSyntax.PREFIX_TAG + "School\n"
            + "To add an income with remark and tag: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + "Bursary " + CliSyntax.PREFIX_VALUE + "1000 "
            + CliSyntax.PREFIX_REMARK + "From SDCC " + CliSyntax.PREFIX_TAG + "School";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an income transaction to THRIFT.\n"
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME DESCRIPTION "
            + CliSyntax.PREFIX_VALUE + "VALUE "
            + "[" + CliSyntax.PREFIX_REMARK + "REMARK] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Bursary "
            + CliSyntax.PREFIX_VALUE + "500 "
            + CliSyntax.PREFIX_REMARK + "For studying well "
            + CliSyntax.PREFIX_TAG + "Award ";

    public static final String MESSAGE_SUCCESS = "New income added: %1$s";

    public static final String UNDO_SUCCESS = "Deleted income: %1$s";
    public static final String REDO_SUCCESS = "Added income: %1$s";

    private final Income toAdd;

    /**
     * Creates an AddIncomeCommand to add the specified {@code Income}
     */
    public AddIncomeCommand(Income income) {
        requireNonNull(income);
        toAdd = income;
    }

    @Override
    public CommandResult execute(Model model, TransactionListPanel transactionListPanel) {
        requireNonNull(model);
        model.addIncome(toAdd);

        // Use null comparison instead of requireNonNull(transactionListPanel) as current JUnit tests are unable to
        // handle JavaFX initialization
        if (model.isInView(toAdd) && transactionListPanel != null) {
            int addIndex = model.getFilteredTransactionList().size() - 1;
            transactionListPanel.getTransactionListView().scrollTo(addIndex);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddIncomeCommand // instanceof handles nulls
                && toAdd.equals(((AddIncomeCommand) other).toAdd));
    }

    @Override
    public String undo(Model model) {
        requireNonNull(model);
        Transaction deletedTransaction = model.deleteLastTransaction();
        return String.format(UNDO_SUCCESS, deletedTransaction);
    }

    @Override
    public String redo(Model model) {
        requireAllNonNull(model, toAdd);
        model.addIncome(toAdd);
        return String.format(REDO_SUCCESS, toAdd);
    }
}
