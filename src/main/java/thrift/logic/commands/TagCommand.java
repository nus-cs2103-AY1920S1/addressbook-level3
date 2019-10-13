package thrift.logic.commands;

import static java.util.Objects.requireNonNull;

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

/**
 * Tags a specified Transaction
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "New tags will be added to existing tags.\n"
            + "Parameters: " + CliSyntax.PREFIX_INDEX + "INDEX (must be a positive integer) "
            + CliSyntax.PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_INDEX + "1 "
            + CliSyntax.PREFIX_TAG + "Food";

    public static final String MESSAGE_TAG_TRANSACTION_SUCCESS = "Updated Transaction: %1$s";
    public static final String MESSAGE_TAG_EXISTED = "\nTag(s) %1$s already exist and will be ignored.";
    public static final String MESSAGE_ORIGINAL_TRANSACTION = "\n\nOriginal: %1$s";
    public static final String MESSAGE_NOT_TAGGED = "At least one tag must be provided.";
    public static final String MESSAGE_NO_NEW_TAGS = "Specified tag(s) already exist, the transaction was not updated.";

    private final Index index;
    private final Set<Tag> tagSet;
    private final StringBuilder existedTags;

    /**
     * Creates a TagCommand to tag the specified {@code Transaction}
     *
     * @param index  of the transaction in the filtered transaction list to update
     * @param tagSet of new tags to be added to the current set of tags, without duplicates.
     */
    public TagCommand(Index index, Set<Tag> tagSet) {
        requireNonNull(index);
        requireNonNull(tagSet);

        this.index = index;
        this.tagSet = tagSet;
        existedTags = new StringBuilder();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToTag = lastShownList.get(index.getZeroBased());
        String originalTransactionNotification = String.format(MESSAGE_ORIGINAL_TRANSACTION, transactionToTag);
        Transaction updatedTransaction = createTaggedTransaction(transactionToTag, tagSet);
        String taggedTransactionNotification = String.format(MESSAGE_TAG_TRANSACTION_SUCCESS, updatedTransaction);
        String existedTagsNotification = existedTags.length() == 0
                ? ""
                : String.format(MESSAGE_TAG_EXISTED, existedTags.toString());

        model.setTransaction(transactionToTag, updatedTransaction);
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);

        return new CommandResult(taggedTransactionNotification
                + existedTagsNotification
                + originalTransactionNotification);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagCommand // instanceof handles nulls
                && index.equals(((TagCommand) other).index)
                && tagSet.equals(((TagCommand) other).tagSet));
    }

    /**
     * Creates and returns a {@code Transaction}
     * with the new tags from {@code tagSet} appended without duplicates.
     */
    private Transaction createTaggedTransaction(Transaction transactionToTag,
            Set<Tag> tagSet) throws CommandException {
        assert transactionToTag != null;

        Description oldDescription = transactionToTag.getDescription();
        Value oldValue = transactionToTag.getValue();
        TransactionDate oldDate = transactionToTag.getDate();
        Remark oldRemark = transactionToTag.getRemark();

        Set<Tag> updatedTags = new HashSet<Tag>(transactionToTag.getTags());

        int noOfTagsIgnored = 0;

        for (Tag newTag : tagSet) {
            if (updatedTags.contains(newTag)) {
                existedTags.append("[" + newTag.tagName + "]");
                noOfTagsIgnored++;
            } else {
                updatedTags.add(newTag);
            }
        }

        if (tagSet.size() == noOfTagsIgnored) {
            throw new CommandException(MESSAGE_NO_NEW_TAGS);
        }

        if (transactionToTag instanceof Expense) {
            return new Expense(oldDescription, oldValue, oldRemark, oldDate, updatedTags);
        } else {
            return new Income(oldDescription, oldValue, oldRemark, oldDate, updatedTags);
        }
    }
}
