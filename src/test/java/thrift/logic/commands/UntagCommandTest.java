package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.PastUndoableCommands;
import thrift.model.UserPrefs;
import thrift.model.tag.Tag;
import thrift.model.transaction.Expense;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.TagSetBuilder;
import thrift.testutil.TypicalIndexes;
import thrift.testutil.TypicalTransactions;

class UntagCommandTest {


    @Test
    void execute_existTags_success() throws CommandException {
        Model model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs(),
                new PastUndoableCommands());
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
        untagCommand.execute(model);
        assertEquals(updatedExpense.getTags(), model.getFilteredTransactionList().get(0).getTags()); //After
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

