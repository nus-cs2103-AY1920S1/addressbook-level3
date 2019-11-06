package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.commands.CommandTestUtil.assertCommandFailure;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertRedoCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertUndoCommandSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.UserPrefs;
import thrift.model.tag.Tag;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.IncomeBuilder;
import thrift.testutil.TagSetBuilder;
import thrift.testutil.TypicalIndexes;
import thrift.testutil.TypicalTransactions;

class UntagCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());
    }

    @Test
    void execute_existTags_success() throws CommandException {
        Expense originalExpense = new ExpenseBuilder(model.getFilteredTransactionList().get(0))
                .withTags("Debt", "Horror", "Bliss").build();
        model.setTransaction(model.getFilteredTransactionList().get(0), originalExpense);

        Set<Tag> tagSet = new TagSetBuilder("Debt", "Horror").build();
        UntagCommand untagCommand = new UntagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, tagSet);

        Set<Tag> expectedTag = new TagSetBuilder("Bliss")
                .build();
        Expense updatedExpense = new Expense(
                originalExpense.getDescription(),
                originalExpense.getValue(),
                originalExpense.getRemark(),
                originalExpense.getDate(),
                expectedTag);

        assertNotEquals(updatedExpense.getTags(), model.getFilteredTransactionList().get(0).getTags()); //Before
        untagCommand.execute(model, null);
        assertEquals(updatedExpense.getTags(), model.getFilteredTransactionList().get(0).getTags()); //After
    }

    @Test
    void execute_nonExistentTagsPresent_success() throws CommandException {
        Income originalIncome = new IncomeBuilder(model.getFilteredTransactionList().get(1))
                .withTags("Test", "Untag", "Freedom").build();
        model.setTransactionWithIndex(TypicalIndexes.INDEX_SECOND_TRANSACTION, originalIncome);

        Set<Tag> tagSet = new TagSetBuilder("Test", "Untag", "Woohoo").build();
        UntagCommand untagCommand = new UntagCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION, tagSet);

        Set<Tag> expectedRemainingTag = new TagSetBuilder("Freedom")
                .build();
        Income updatedIncome = new Income(
                originalIncome.getDescription(),
                originalIncome.getValue(),
                originalIncome.getRemark(),
                originalIncome.getDate(),
                expectedRemainingTag);

        String taggedTransactionNotification = String.format(UntagCommand.MESSAGE_UNTAG_TRANSACTION_SUCCESS,
                updatedIncome);
        String nonexistentTagsNotification = String.format(UntagCommand.MESSAGE_TAG_NOT_EXISTED, "[Woohoo]");
        String originalTransactionNotification = String.format(UntagCommand.MESSAGE_ORIGINAL_TRANSACTION,
                originalIncome);
        String expectedMessage = taggedTransactionNotification + nonexistentTagsNotification
                + originalTransactionNotification;

        ModelManager expectedModel = new ModelManager(model.getThrift(), new UserPrefs());
        expectedModel.setTransactionWithIndex(TypicalIndexes.INDEX_SECOND_TRANSACTION, updatedIncome);

        assertCommandSuccess(untagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void invalidIndexField_throwsCommandException() {
        Index index = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        UntagCommand untagCommand = new UntagCommand(index, new TagSetBuilder().addTag("Test").build());

        assertCommandFailure(untagCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    void noTagsDeleted_throwsCommandException() {
        Expense expense = new ExpenseBuilder(model.getFilteredTransactionList().get(0))
                .withTags("Test", "Untag").build();
        model.setTransactionWithIndex(TypicalIndexes.INDEX_THIRD_TRANSACTION, expense);

        Set<Tag> tagSet = new TagSetBuilder("Different", "Nothing").build();
        UntagCommand untagCommand = new UntagCommand(TypicalIndexes.INDEX_THIRD_TRANSACTION, tagSet);

        assertCommandFailure(untagCommand, model, UntagCommand.MESSAGE_NO_DEL_TAGS);
    }

    @Test
    void undoAndRedo_untagForExpense_success() {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs());
        Transaction lastTransaction = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_THIRD_TRANSACTION.getZeroBased());
        //assuming that untag command untags valid tag
        Set<Tag> tagSet = new TagSetBuilder("Brunch").build();
        Set<Tag> updatedTags = new HashSet<>(lastTransaction.getTags());
        for (Tag tag : tagSet) {
            updatedTags.remove(tag);
        }
        Transaction updatedTransaction = new Expense(lastTransaction.getDescription(), lastTransaction.getValue(),
                lastTransaction.getRemark(), lastTransaction.getDate(), updatedTags);
        String expectedMessageOriginal = String.format(UntagCommand.MESSAGE_ORIGINAL_TRANSACTION, lastTransaction);
        String expectedMessageUpdated = String.format(UntagCommand.MESSAGE_UNTAG_TRANSACTION_SUCCESS,
                updatedTransaction);

        //tests untag command
        UntagCommand untagCommand = new UntagCommand(TypicalIndexes.INDEX_THIRD_TRANSACTION, tagSet);
        expectedModel.setTransactionWithIndex(TypicalIndexes.INDEX_THIRD_TRANSACTION, updatedTransaction);
        assertCommandSuccess(untagCommand, model, expectedMessageUpdated + expectedMessageOriginal,
                expectedModel);

        //tests undo command
        expectedModel.setTransactionWithIndex(TypicalIndexes.INDEX_THIRD_TRANSACTION, lastTransaction);
        assertUndoCommandSuccess(untagCommand, model, expectedModel);

        //tests redo command
        expectedModel.setTransactionWithIndex(TypicalIndexes.INDEX_THIRD_TRANSACTION, updatedTransaction);
        assertRedoCommandSuccess(untagCommand, model, expectedModel);
    }

    @Test
    void undoAndRedo_untagForIncome_success() {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs());
        Transaction lastTransaction = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_SECOND_TRANSACTION.getZeroBased());
        //assuming that untag command untags valid tag
        Set<Tag> tagSet = new TagSetBuilder("Award").build();
        Set<Tag> updatedTags = new HashSet<>(lastTransaction.getTags());
        for (Tag tag : tagSet) {
            updatedTags.remove(tag);
        }
        Transaction updatedTransaction = new Income(lastTransaction.getDescription(), lastTransaction.getValue(),
                lastTransaction.getRemark(), lastTransaction.getDate(), updatedTags);
        String expectedMessageOriginal = String.format(UntagCommand.MESSAGE_ORIGINAL_TRANSACTION, lastTransaction);
        String expectedMessageUpdated = String.format(UntagCommand.MESSAGE_UNTAG_TRANSACTION_SUCCESS,
                updatedTransaction);

        //tests untag command
        UntagCommand untagCommand = new UntagCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION, tagSet);
        expectedModel.setTransactionWithIndex(TypicalIndexes.INDEX_SECOND_TRANSACTION, updatedTransaction);
        assertCommandSuccess(untagCommand, model, expectedMessageUpdated + expectedMessageOriginal,
                expectedModel);

        //tests undo command
        expectedModel.setTransactionWithIndex(TypicalIndexes.INDEX_SECOND_TRANSACTION, lastTransaction);
        assertUndoCommandSuccess(untagCommand, model, expectedModel);

        //tests redo command
        expectedModel.setTransactionWithIndex(TypicalIndexes.INDEX_SECOND_TRANSACTION, updatedTransaction);
        assertRedoCommandSuccess(untagCommand, model, expectedModel);
    }

    @Test
    void testEquals() {
        Set<Tag> setOne = new TagSetBuilder("Food", "Shopping").build();
        Set<Tag> setTwo = new TagSetBuilder("Debt", "Fees").build();
        UntagCommand firstUntagCommand = new UntagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, setOne);
        UntagCommand secondUntagCommand = new UntagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, setTwo);

        // same object -> returns true
        assertTrue(firstUntagCommand.equals(firstUntagCommand));

        // same values -> returns true
        UntagCommand firstUntagCommandCopy = new UntagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, setOne);
        assertTrue(firstUntagCommand.equals(firstUntagCommandCopy));

        // different types -> returns false
        assertFalse(firstUntagCommand.equals(1));

        // null -> returns false
        assertFalse(firstUntagCommand.equals(null));

        // different transaction -> returns false
        assertFalse(firstUntagCommand.equals(secondUntagCommand));
    }

}

