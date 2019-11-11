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
public class AddTagCommandTest {
    private static final List<String> SINGLE_TAG_STUB = new ArrayList<>(Collections.singletonList("school"));
    private static final List<String> DOUBLE_TAG_STUB = new ArrayList<>(Arrays.asList("school", "home"));

    private Model model = new ModelManager(getTypicalBillboard(), new UserPrefs());

    @Test
    public void execute_addTag_success() {
        Expense firstExpense = model.getFilteredExpenses().get(INDEX_FIRST_EXPENSE.getZeroBased());
        Expense editedExpense = new ExpenseBuilder(firstExpense).withExistingTags(SINGLE_TAG_STUB.get(0)).build();

        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_EXPENSE, SINGLE_TAG_STUB);
        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new Billboard(model.getBillboard()), new UserPrefs());
        expectedModel.setExpense(firstExpense, editedExpense);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addMultipleTags_success() {
        Expense firstExpense = model.getFilteredExpenses().get(INDEX_FIRST_EXPENSE.getZeroBased());
        Expense editedExpense = new ExpenseBuilder(firstExpense).withExistingTags(DOUBLE_TAG_STUB.get(0),
                DOUBLE_TAG_STUB.get(1)).build();

        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_EXPENSE, DOUBLE_TAG_STUB);
        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new Billboard(model.getBillboard()), new UserPrefs());
        expectedModel.setExpense(firstExpense, editedExpense);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenses().size() + 1);
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, SINGLE_TAG_STUB);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_existingTags_failure() {
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_EXPENSE, Collections.singletonList("bills"));

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_ADD_TAG_FAILURE);
    }

    @Test
    public void equals() {
        final AddTagCommand standardCommand = new AddTagCommand(INDEX_FIRST_EXPENSE,
                SINGLE_TAG_STUB);

        // same values -> returns true
        AddTagCommand commandWithSameValues = new AddTagCommand(INDEX_FIRST_EXPENSE,
                SINGLE_TAG_STUB);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new AddTagCommand(INDEX_SECOND_EXPENSE,
                SINGLE_TAG_STUB));

        // different tag names -> returns false
        assertNotEquals(standardCommand, new AddTagCommand(INDEX_FIRST_EXPENSE,
                DOUBLE_TAG_STUB));
    }
}
