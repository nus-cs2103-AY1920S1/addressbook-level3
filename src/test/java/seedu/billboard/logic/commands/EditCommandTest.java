package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.commons.core.index.Index;
import seedu.billboard.model.Billboard;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.testutil.EditExpenseDescriptorBuilder;
import seedu.billboard.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalBillboard(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Expense editedExpense = new ExpenseBuilder().build();
        EditCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(editedExpense).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new Billboard(model.getBillboard()), new UserPrefs());
        expectedModel.setExpense(model.getFilteredExpenses().get(0), editedExpense);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExpense = Index.fromOneBased(model.getFilteredExpenses().size());
        Expense lastExpense = model.getFilteredExpenses().get(indexLastExpense.getZeroBased());

        ExpenseBuilder expenseInList = new ExpenseBuilder(lastExpense);
        Expense editedExpense = expenseInList.withName(VALID_NAME_TAXES).withDescription(VALID_DESCRIPTION_TAXES)
                .withTags(VALID_TAG_DINNER).build();

        EditCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withName(VALID_NAME_TAXES)
                .withDescription(VALID_DESCRIPTION_TAXES).withTags(VALID_TAG_DINNER).build();
        EditCommand editCommand = new EditCommand(indexLastExpense, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new Billboard(model.getBillboard()), new UserPrefs());
        expectedModel.setExpense(lastExpense, editedExpense);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditCommand.EditExpenseDescriptor());
        Expense editedExpense = model.getFilteredExpenses().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new Billboard(model.getBillboard()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Expense expenseInFilteredList = model.getFilteredExpenses().get(INDEX_FIRST_PERSON.getZeroBased());
        Expense editedExpense = new ExpenseBuilder(expenseInFilteredList).withName(VALID_NAME_TAXES).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditExpenseDescriptorBuilder().withName(VALID_NAME_TAXES).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new Billboard(model.getBillboard()), new UserPrefs());
        expectedModel.setExpense(model.getFilteredExpenses().get(0), editedExpense);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Expense firstExpense = model.getFilteredExpenses().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(firstExpense).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit expense in filtered list into a duplicate in address book
        Expense expenseInList = model.getBillboard().getExpenses().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand.EditExpenseDescriptor thing = new EditExpenseDescriptorBuilder(expenseInList).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditExpenseDescriptorBuilder(expenseInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenses().size() + 1);
        EditCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withName(VALID_NAME_TAXES).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBillboard().getExpenses().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditExpenseDescriptorBuilder().withName(VALID_NAME_TAXES).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_DINNER);

        // same values -> returns true
        EditCommand.EditExpenseDescriptor copyDescriptor = new EditCommand.EditExpenseDescriptor(DESC_DINNER);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new EditCommand(INDEX_SECOND_PERSON, DESC_DINNER));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditCommand(INDEX_FIRST_PERSON, DESC_TAXES));
    }

}
