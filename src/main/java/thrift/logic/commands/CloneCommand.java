package thrift.logic.commands;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;
import static thrift.model.transaction.TransactionDate.DATE_FORMATTER;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.logic.commands.exceptions.CommandException;
import thrift.logic.parser.CliSyntax;
import thrift.model.Model;
import thrift.model.clone.Occurrence;
import thrift.model.tag.Tag;
import thrift.model.transaction.Description;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Remark;
import thrift.model.transaction.Transaction;
import thrift.model.transaction.TransactionDate;
import thrift.model.transaction.Value;
import thrift.ui.TransactionListPanel;

/**
 * Clones a transaction specified by its index in THRIFT.
 */
public class CloneCommand extends ScrollingCommand implements Undoable {

    public static final String COMMAND_WORD = "clone";

    public static final String HELP_MESSAGE = COMMAND_WORD
            + ": Clones the transaction specified by its index number used in the displayed transaction list.\n"
            + "Format: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "INDEX (must be a positive integer)\n"
            + "[" + CliSyntax.PREFIX_OCCURRENCE
            + "OCCURRENCE (FREQUENCY:NUMBER_OF_OCCURRENCES)]"
            + "\n- Valid FREQUENCY values are: \"daily\", \"weekly\", \"monthly\", \"yearly\""
            + "\n- Valid NUMBER_OF_OCCURRENCES ranges are: 1 - 5 with \"yearly\", 1 - 12 with other frequencies"
            + "\nPossible usage of " + COMMAND_WORD + ": \n"
            + "To clone the transaction at index 8 in the displayed transaction list: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "8\n"
            + "To clone the transaction at index 8 5 times across next 5 months (including current month):"
            + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "8 " + CliSyntax.PREFIX_OCCURRENCE + "monthly:5";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clones the transaction specified by its index number used in the displayed transaction list.\n"
            + "Parameters: " + CliSyntax.PREFIX_INDEX + "INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_OCCURRENCE
            + "OCCURRENCE (FREQUENCY:NUMBER_OF_OCCURRENCES)]"
            + "\n- Valid FREQUENCY values are: \"daily\", \"weekly\", \"monthly\", \"yearly\""
            + "\n- Valid NUMBER_OF_OCCURRENCES ranges are: 1 - 5 with \"yearly\", 1 - 12 with other frequencies"
            + "\nExample: " + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "1 "
            + CliSyntax.PREFIX_OCCURRENCE + "monthly:5";

    public static final String MESSAGE_CLONE_TRANSACTION_SUCCESS = "Cloned transaction: %1$s";
    public static final String MESSAGE_NUM_CLONED_TRANSACTIONS = "(Cloned %s %d times)";

    public static final String UNDO_SUCCESS = "Deleted cloned transaction: %1$s";
    public static final String REDO_SUCCESS = "Added cloned transaction: %1$s";

    private final Index targetIndex;
    private final Occurrence occurrence;
    private Transaction clonedTransaction;

    /**
     * Creates a CloneCommand instance to clone an {@code Expense} or {@code Income}
     *
     * @param targetIndex from the displayed list of the transaction to be cloned
     * @param occurrence representing frequency and number of times cloned items occur
     */
    public CloneCommand(Index targetIndex, Occurrence occurrence) {
        requireNonNull(targetIndex);
        requireNonNull(occurrence);
        this.targetIndex = targetIndex;
        this.occurrence = occurrence;
        this.clonedTransaction = null;
    }

    @Override
    public CommandResult execute(Model model, TransactionListPanel transactionListPanel) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToClone = lastShownList.get(targetIndex.getZeroBased());
        int frequencyCalendarField = occurrence.getFrequencyCalendarField();

        for (int i = 0; i < occurrence.getNumOccurrences(); i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(frequencyCalendarField, i);
            String date = DATE_FORMATTER.format(calendar.getTime());

            clonedTransaction = createClonedTransaction(transactionToClone, date);
            if (clonedTransaction instanceof Expense) {
                model.addExpense((Expense) clonedTransaction);
            } else if (clonedTransaction instanceof Income) {
                model.addIncome((Income) clonedTransaction);
            }

            // Use null comparison instead of requireNonNull(transactionListPanel) as current JUnit tests are unable to
            // handle JavaFX initialization
            if (transactionListPanel != null && model.isInView(clonedTransaction)) {
                transactionListPanel.getTransactionListView().scrollTo(model.getFilteredTransactionList().size() - 1);
            }
        }

        return new CommandResult(String.format(MESSAGE_CLONE_TRANSACTION_SUCCESS, transactionToClone)
                + "\n" + String.format(MESSAGE_NUM_CLONED_TRANSACTIONS, occurrence.getFrequency(),
                occurrence.getNumOccurrences()));
    }

    /**
     * Creates a clone of the transaction at {@link #targetIndex} of the displayed list.
     *
     * @param transactionToClone {@link Transaction} that a clone should be created of, with current Date.
     * @param date that the cloned Transaction should contain.
     * @return {@link Expense} or {@link Income} clone of {@code transactionToClone} containing current Date.
     */
    private Transaction createClonedTransaction(Transaction transactionToClone, String date) {

        Description clonedDescription = transactionToClone.getDescription();
        Value clonedValue = transactionToClone.getValue();
        Remark clonedRemark = transactionToClone.getRemark();
        Set<Tag> clonedTags = transactionToClone.getTags();
        TransactionDate currentDate = new TransactionDate(date);
        if (transactionToClone instanceof Expense) {
            return new Expense(clonedDescription, clonedValue, clonedRemark, currentDate, clonedTags);
        } else {
            return new Income(clonedDescription, clonedValue, clonedRemark, currentDate, clonedTags);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CloneCommand // instanceof handles nulls
                && targetIndex.equals(((CloneCommand) other).targetIndex))
                && occurrence.equals(((CloneCommand) other).occurrence); // state check
    }

    @Override
    public String undo(Model model) {
        requireNonNull(model);
        Transaction deletedTransaction = model.deleteLastTransaction();
        return String.format(UNDO_SUCCESS, deletedTransaction);
    }

    @Override
    public String redo(Model model) {
        requireAllNonNull(model, clonedTransaction);
        if (clonedTransaction instanceof Expense) {
            model.addExpense((Expense) clonedTransaction);
        } else if (clonedTransaction instanceof Income) {
            model.addIncome((Income) clonedTransaction);
        }
        return String.format(REDO_SUCCESS, clonedTransaction);
    }
}
