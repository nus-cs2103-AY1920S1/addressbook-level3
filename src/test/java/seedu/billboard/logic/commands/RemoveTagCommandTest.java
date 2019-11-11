package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.commons.core.index.Index;
import seedu.billboard.model.Billboard;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.testutil.ExpenseBuilder;

//@@author waifonglee
public class RemoveTagCommandTest {
    private static final List<String> SINGLE_TAG_STUB = new ArrayList<>(Collections.singletonList("friends"));
    private static final List<String> DOUBLE_TAG_STUB = new ArrayList<>(Arrays.asList("friends", "monday"));

    private Model model = new ModelManager(getTypicalBillboard(), new UserPrefs());

    @Test
    public void execute_removeTag_success() {
        Expense secondExpense = model.getFilteredExpenses().get(INDEX_SECOND_EXPENSE.getZeroBased());
        Expense editedExpense = new ExpenseBuilder(secondExpense).withTags("monday").build();

        RemoveTagCommand rmTagCommand = new RemoveTagCommand(INDEX_SECOND_EXPENSE, SINGLE_TAG_STUB);
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_RM_TAG_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new Billboard(model.getBillboard()), new UserPrefs());
        expectedModel.setExpense(secondExpense, editedExpense);

        assertCommandSuccess(rmTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeMultipleTags_success() {
        Expense secondExpense = model.getFilteredExpenses().get(INDEX_SECOND_EXPENSE.getZeroBased());
        Expense editedExpense = new ExpenseBuilder(secondExpense).removeAllTags().build();

        RemoveTagCommand rmTagCommand = new RemoveTagCommand(INDEX_SECOND_EXPENSE, DOUBLE_TAG_STUB);
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_RM_TAG_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new Billboard(model.getBillboard()), new UserPrefs());
        expectedModel.setExpense(secondExpense, editedExpense);

        assertCommandSuccess(rmTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenses().size() + 1);
        RemoveTagCommand rmTagCommand = new RemoveTagCommand(outOfBoundIndex, SINGLE_TAG_STUB);

        assertCommandFailure(rmTagCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_removeNonExistingTags_failure() {
        RemoveTagCommand rmTagCommand = new RemoveTagCommand(INDEX_SECOND_EXPENSE,
                Collections.singletonList("transport"));

        assertCommandFailure(rmTagCommand, model, RemoveTagCommand.MESSAGE_RM_TAG_FAILURE);

    }

    @Test
    public void equals() {
        final RemoveTagCommand standardCommand = new RemoveTagCommand(INDEX_SECOND_EXPENSE,
                SINGLE_TAG_STUB);

        // same values -> returns true
        RemoveTagCommand commandWithSameValues = new RemoveTagCommand(INDEX_SECOND_EXPENSE,
                SINGLE_TAG_STUB);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new RemoveTagCommand(INDEX_FIRST_EXPENSE,
                SINGLE_TAG_STUB));

        // different tag names -> returns false
        assertNotEquals(standardCommand, new RemoveTagCommand(INDEX_SECOND_EXPENSE,
                DOUBLE_TAG_STUB));
    }
}
