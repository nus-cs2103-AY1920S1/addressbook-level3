package seedu.guilttrip.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_DESC_SALARY_INCOME;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_DESC_TUITION_INCOME;
import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.guilttrip.logic.commands.CommandTestUtil.showIncomeAtIndex;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalGuiltTrip;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.editcommands.EditIncomeCommand;
import seedu.guilttrip.logic.commands.editcommands.EditIncomeCommand.EditIncomeDescriptor;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.UserPrefs;
import seedu.guilttrip.testutil.EditIncomeDescriptorBuilder;


public class EditIncomeCommandTest {

    private Model model = new ModelManager(getTypicalGuiltTrip(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /*@Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Income editedIncome = new IncomeBuilder().build();
        EditIncomeCommand.EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder(editedIncome).build();
        EditIncomeCommand editCommand = new EditIncomeCommand(INDEX_FIRST_ENTRY, descriptor);

        String expectedMessage = String.format(EditIncomeCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedIncome);

        Model expectedModel = new ModelManager(new GuiltTrip(model.getGuiltTrip()), new UserPrefs());
        expectedModel.setIncome(model.getFilteredIncomes().get(0), editedIncome);
        expectedModel.commitGuiltTrip();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, commandHistory);
    }*/

    /*@Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastIncome = Index.fromOneBased(model.getFilteredIncomes().size());
        Income lastIncome = model.getFilteredIncomes().get(indexLastIncome.getZeroBased());

        IncomeBuilder incomeInList = new IncomeBuilder(lastIncome);
        Income editedIncome = incomeInList.withDesc(VALID_DESC_SALARY_INCOME).withAmt(VALID_AMOUNT_SALARY_INCOME)
                .build();

        EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder().withDescription(VALID_DESC_SALARY_INCOME)
                .withAmount(VALID_AMOUNT_SALARY_INCOME).build();
        EditIncomeCommand editIncomeCommand = new EditIncomeCommand(indexLastIncome, descriptor);

        String expectedMessage = String.format(EditIncomeCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedIncome);

        Model expectedModel = new ModelManager(new GuiltTrip(model.getGuiltTrip()), new UserPrefs());
        expectedModel.setIncome(lastIncome, editedIncome);
        expectedModel.commitGuiltTrip();

        assertCommandSuccess(editIncomeCommand, model, expectedMessage, expectedModel, commandHistory);
    }*/

    /*@Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditIncomeCommand editIncomeCommand = new EditIncomeCommand(INDEX_FIRST_ENTRY, new EditIncomeDescriptor());
        Income editedIncome = model.getFilteredIncomes().get(INDEX_FIRST_ENTRY.getZeroBased());

        String expectedMessage = String.format(EditIncomeCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedIncome);

        Model expectedModel = new ModelManager(new GuiltTrip(model.getGuiltTrip()), new UserPrefs());
        expectedModel.commitGuiltTrip();

        assertCommandSuccess(editIncomeCommand, model, expectedMessage, expectedModel, commandHistory);
    }*/

    /*@Test
    public void execute_filteredList_success() {
        showIncomeAtIndex(model, INDEX_FIRST_ENTRY);

        Income incomeInFilteredList = model.getFilteredIncomes().get(INDEX_FIRST_ENTRY.getZeroBased());
        Income editedIncome = new IncomeBuilder(incomeInFilteredList).withDesc(VALID_DESC_SALARY_INCOME).build();
        EditIncomeCommand editIncomeCommand = new EditIncomeCommand(INDEX_FIRST_ENTRY,
                new EditIncomeDescriptorBuilder().withDescription(VALID_DESC_SALARY_INCOME).build());

        String expectedMessage = String.format(EditIncomeCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedIncome);

        Model expectedModel = new ModelManager(new GuiltTrip(model.getGuiltTrip()), new UserPrefs());
        expectedModel.setIncome(model.getFilteredIncomes().get(0), editedIncome);
        expectedModel.commitGuiltTrip();

        assertCommandSuccess(editIncomeCommand, model, expectedMessage, expectedModel, commandHistory);
    }*/

    @Test
    public void execute_invalidIncomeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIncomes().size() + 1);
        EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder().withDescription(VALID_DESC_SALARY_INCOME)
                .build();
        EditIncomeCommand editIncomeCommand = new EditIncomeCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editIncomeCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX, commandHistory);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of GuiltTrip
     */
    @Test
    public void execute_invalidIncomeIndexFilteredList_failure() {
        showIncomeAtIndex(model, INDEX_FIRST_ENTRY);
        Index outOfBoundIndex = INDEX_SECOND_ENTRY;
        // ensures that outOfBoundIndex is still in bounds of GuiltTrip list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getGuiltTrip().getIncomeList().size());

        EditIncomeCommand editCommand = new EditIncomeCommand(outOfBoundIndex,
                new EditIncomeDescriptorBuilder().withDescription(VALID_DESC_SALARY_INCOME).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX, commandHistory);
    }

    /*@Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Income editedIncome = new IncomeBuilder().build();
        Income incomeToEdit = model.getFilteredIncomes().get(INDEX_FIRST_ENTRY.getZeroBased());
        EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder(editedIncome).build();
        EditIncomeCommand editIncomeCommand = new EditIncomeCommand(INDEX_FIRST_ENTRY, descriptor);
        Model expectedModel = new ModelManager(new GuiltTrip(model.getGuiltTrip()), new UserPrefs());
        expectedModel.setIncome(incomeToEdit, editedIncome);
        expectedModel.commitGuiltTrip();

        // edit -> first income edited
        editIncomeCommand.execute(model, commandHistory);

        // undo -> reverts GuiltTrip back to previous state and filtered income list to show all incomes
        expectedModel.undoGuiltTrip();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first income edited again
        expectedModel.redoGuiltTrip();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/

    /*@Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIncomes().size() + 1);
        EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder().withDescription(VALID_DESC_SALARY_INCOME)
                .build();
        EditIncomeCommand editIncomeCommand = new EditIncomeCommand(outOfBoundIndex, descriptor);

        // execution failed -> GuiltTrip state not added into model
        assertCommandFailure(editIncomeCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX, commandHistory);

        // single GuiltTrip state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }*/

    /**
     * 1. Edits a {@code Income} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited income in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the income object regardless of indexing.
     */
    /*@Test
    public void executeUndoRedo_validIndexFilteredList_sameIncomeEdited() throws Exception {
        Income editedIncome = new IncomeBuilder().build();
        EditIncomeCommand.EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder(editedIncome).build();
        EditIncomeCommand editCommand = new EditIncomeCommand(INDEX_FIRST_ENTRY, descriptor);
        Model expectedModel = new ModelManager(new GuiltTrip(model.getGuiltTrip()), new UserPrefs());

        showIncomeAtIndex(model, INDEX_SECOND_ENTRY);
        Income incomeToEdit = model.getFilteredIncomes().get(INDEX_FIRST_ENTRY.getZeroBased());
        expectedModel.setIncome(incomeToEdit, editedIncome);
        expectedModel.commitGuiltTrip();

        // edit -> edits second income in unfiltered income list / first income in filtered income list
        editCommand.execute(model, commandHistory);

        // undo -> reverts GuiltTrip back to previous state and filtered income list to show all incomes
        expectedModel.undoGuiltTrip();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredIncomes().get(INDEX_FIRST_ENTRY.getZeroBased()), incomeToEdit);
        // redo -> edits same second income in unfiltered income list
        expectedModel.redoGuiltTrip();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/
    @Test
    public void equals() {
        EditIncomeCommand standardCommand = new EditIncomeCommand(INDEX_FIRST_ENTRY,
                new EditIncomeDescriptorBuilder().withDescription(VALID_DESC_SALARY_INCOME).build());

        // same values -> returns true
        EditIncomeDescriptor copyDescriptor1 = new EditIncomeDescriptorBuilder()
                .withDescription(VALID_DESC_SALARY_INCOME).build();
        EditIncomeCommand commandWithSameValues = new EditIncomeCommand(INDEX_FIRST_ENTRY, copyDescriptor1);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> not equals returns true
        assertNotEquals(null, standardCommand);

        // different types -> not equals returns true
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> not equals returns false
        assertNotEquals(standardCommand, new EditIncomeCommand(INDEX_SECOND_ENTRY, copyDescriptor1));

        // different descriptor -> not equals returns false
        EditIncomeDescriptor copyDescriptor2 = new EditIncomeDescriptorBuilder()
                .withDescription(VALID_DESC_TUITION_INCOME).build();
        assertNotEquals(standardCommand, new EditIncomeCommand(INDEX_FIRST_ENTRY, copyDescriptor2));
    }

}
