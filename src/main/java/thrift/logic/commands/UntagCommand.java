package thrift.logic.commands;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.logic.commands.exceptions.CommandException;
import thrift.logic.parser.CliSyntax;
import thrift.model.Model;
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
 * Tags a specified Transaction
 */
public class UntagCommand extends ScrollingCommand implements Undoable {

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Untags the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "The tags will be removed from existing tags, if they exist.\n"
            + "Parameters: " + CliSyntax.PREFIX_INDEX + "INDEX (must be a positive integer) "
            + CliSyntax.PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_INDEX + "1 "
            + CliSyntax.PREFIX_TAG + "Food";

    public static final String HELP_MESSAGE = COMMAND_WORD
            + ": Untags the transaction identified by the index number used in the displayed transaction list. "
            + "The tags will be removed from existing tags, if they exist.\n"
            + "Format: " + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "INDEX (must be a positive integer) "
            + CliSyntax.PREFIX_TAG + "TAG...\n"
            + "Possible usages of " + COMMAND_WORD + ": \n"
            + "To remove a tag from existing tag(s) of the transaction at index 1 in the displayed transaction list: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "1 " + CliSyntax.PREFIX_TAG + "Food\n"
            + "To remove multiple tags from existing tag(s) of the transaction at index 1 in the displayed "
            + "transaction list: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "1 " + CliSyntax.PREFIX_TAG + "Food "
            + CliSyntax.PREFIX_TAG + "Japanese";

    public static final String MESSAGE_UNTAG_TRANSACTION_SUCCESS = "Updated Transaction: %1$s";
    public static final String MESSAGE_TAG_NOT_EXISTED = "\nTag(s) %1$s do not exist and will be ignored.";
    public static final String MESSAGE_ORIGINAL_TRANSACTION = "\n\nOriginal: %1$s";
    public static final String MESSAGE_NOT_UNTAGGED = "At least one tag must be provided.";
    public static final String MESSAGE_NO_DEL_TAGS = "Specified tag(s) do not exist, the transaction was not updated.";
    public static final String UNDO_SUCCESS = "Updated Transaction: %1$s\nOriginal: %2$s";
    public static final String REDO_SUCCESS = "Updated Transaction: %1$s\nOriginal: %2$s";

    private final Index index;
    private final Set<Tag> tagSet;
    private final StringBuilder nonexistentTags;
    private Index actualIndex;
    private Transaction transactionToTag;
    private Transaction updatedTransaction;

    /**
     * Creates a UntagCommand to untag the specified {@code Transaction}
     *
     * @param index of the transaction in the filtered transaction list to update
     * @param tagSet of tags to be removed from the current set of tags, if they exist.
     */
    public UntagCommand(Index index, Set<Tag> tagSet) {
        requireNonNull(index);
        requireNonNull(tagSet);

        this.index = index;
        this.tagSet = tagSet;
        nonexistentTags = new StringBuilder();
        this.actualIndex = null;
        this.transactionToTag = null;
        this.updatedTransaction = null;
    }

    @Override
    public CommandResult execute(Model model, TransactionListPanel transactionListPanel) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        transactionToTag = lastShownList.get(index.getZeroBased());
        String originalTransactionNotification = String.format(MESSAGE_ORIGINAL_TRANSACTION, transactionToTag);
        updatedTransaction = createUntaggedTransaction(transactionToTag, tagSet);
        String taggedTransactionNotification = String.format(MESSAGE_UNTAG_TRANSACTION_SUCCESS, updatedTransaction);
        String nonexistentTagsNotification = nonexistentTags.length() == 0
                ? ""
                : String.format(MESSAGE_TAG_NOT_EXISTED, nonexistentTags.toString());

        actualIndex = model.getIndexInFullTransactionList(transactionToTag).orElse(index);
        model.setTransactionWithIndex(actualIndex, updatedTransaction);

        // Use null comparison instead of requireNonNull(transactionListPanel) as current JUnit tests are unable to
        // handle JavaFX initialization
        if (transactionListPanel != null && model.isInView(updatedTransaction)) {
            int untaggedIndex = index.getZeroBased();
            transactionListPanel.getTransactionListView().scrollTo(untaggedIndex);
        }

        return new CommandResult(taggedTransactionNotification
                + nonexistentTagsNotification
                + originalTransactionNotification);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UntagCommand // instanceof handles nulls
                && index.equals(((UntagCommand) other).index)
                && tagSet.equals(((UntagCommand) other).tagSet));
    }

    /**
     * Creates and returns a {@code Transaction}
     * with the tags from {@code tagSet} removed.
     */
    private Transaction createUntaggedTransaction(Transaction transactionToTag,
            Set<Tag> tagSet) throws CommandException {
        assert transactionToTag != null;

        Description oldDescription = transactionToTag.getDescription();
        Value oldValue = transactionToTag.getValue();
        TransactionDate oldDate = transactionToTag.getDate();
        Remark oldRemark = transactionToTag.getRemark();

        Set<Tag> updatedTags = new HashSet<Tag>(transactionToTag.getTags());

        int noOfTagsIgnored = 0;
        for (Tag targetTag : tagSet) {
            if (!updatedTags.contains(targetTag)) {
                nonexistentTags.append("[" + targetTag.tagName + "]");
                noOfTagsIgnored++;
            } else {
                updatedTags.remove(targetTag);
            }
        }

        if (tagSet.size() == noOfTagsIgnored) {
            throw new CommandException(MESSAGE_NO_DEL_TAGS);
        }

        if (transactionToTag instanceof Expense) {
            return new Expense(oldDescription, oldValue, oldRemark, oldDate, updatedTags);
        } else {
            return new Income(oldDescription, oldValue, oldRemark, oldDate, updatedTags);
        }
    }

    @Override
    public String undo(Model model) {
        requireAllNonNull(model, actualIndex, updatedTransaction, transactionToTag);
        model.setTransactionWithIndex(actualIndex, transactionToTag);
        return String.format(UNDO_SUCCESS, transactionToTag, updatedTransaction);
    }

    @Override
    public String redo(Model model) {
        requireAllNonNull(model, actualIndex, updatedTransaction, transactionToTag);
        model.setTransactionWithIndex(actualIndex, updatedTransaction);
        return String.format(REDO_SUCCESS, updatedTransaction, transactionToTag);
    }
}
