package seedu.moolah.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_AMOUNT_HOLIDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_DESCRIPTION_HOLIDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_PERIOD_HOLIDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_START_DATE_HOLIDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TestUtil.makeModelStack;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.budget.EditBudgetCommand.EditBudgetDescriptor;
import seedu.moolah.logic.commands.general.ClearCommand;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelHistory;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.MooLah;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.testutil.BudgetBuilder;
import seedu.moolah.testutil.EditBudgetDescriptorBuilder;

public class EditBudgetCommandTest {
    private Model model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());

    @Test
    public void run_allFieldsSpecified_success() {
        Budget editedBudget = new BudgetBuilder()
                .withDescription(VALID_BUDGET_DESCRIPTION_HOLIDAY)
                .withAmount(VALID_BUDGET_AMOUNT_HOLIDAY)
                .withStartDate(VALID_BUDGET_START_DATE_HOLIDAY)
                .withPeriod(VALID_BUDGET_PERIOD_HOLIDAY)
                .build();

        EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder(editedBudget).build();

        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(INDEX_SECOND, descriptor);

        String expectedMessage = String.format(EditBudgetCommand.MESSAGE_EDIT_BUDGET_SUCCESS, editedBudget);

        Model expectedModel = new ModelManager(new MooLah(model.getMooLah()),
                new UserPrefs(), new ModelHistory());
        expectedModel.setBudget(model.getFilteredBudgetList().get(INDEX_SECOND.getZeroBased()), editedBudget);
        expectedModel.setModelHistory(new ModelHistory("", makeModelStack(model), makeModelStack()));

        assertCommandSuccess(editBudgetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_someFieldsSpecified_success() {
        Index indexLastBudget = Index.fromOneBased(model.getFilteredBudgetList().size());
        Budget lastBudget = model.getFilteredBudgetList().get(indexLastBudget.getZeroBased());

        BudgetBuilder budgetInList = new BudgetBuilder(lastBudget);
        Budget editedBudget = budgetInList
                .withDescription(VALID_BUDGET_DESCRIPTION_HOLIDAY)
                .withAmount(VALID_BUDGET_AMOUNT_HOLIDAY)
                .build();

        EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withDescription(VALID_BUDGET_DESCRIPTION_HOLIDAY)
                .withAmount(VALID_BUDGET_AMOUNT_HOLIDAY)
                .build();
        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(indexLastBudget, descriptor);

        String expectedMessage = String.format(EditBudgetCommand.MESSAGE_EDIT_BUDGET_SUCCESS, editedBudget);

        Model expectedModel = new ModelManager(new MooLah(model.getMooLah()),
                new UserPrefs(), new ModelHistory());
        expectedModel.setBudget(lastBudget, editedBudget);
        expectedModel.setModelHistory(new ModelHistory("", makeModelStack(model), makeModelStack()));

        assertCommandSuccess(editBudgetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_noFieldSpecified_success() {
        EditBudgetCommand editBudgetCommand =
                new EditBudgetCommand(INDEX_SECOND, new EditBudgetDescriptor());
        Budget editedBudget = model.getFilteredBudgetList().get(INDEX_SECOND.getZeroBased());

        String expectedMessage = String.format(EditBudgetCommand.MESSAGE_EDIT_BUDGET_SUCCESS, editedBudget);

        Model expectedModel = new ModelManager(new MooLah(model.getMooLah()),
                new UserPrefs(), new ModelHistory());
        expectedModel.commitModel("");

        assertCommandSuccess(editBudgetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_invalidBudgetIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBudgetList().size() + 1);
        EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withDescription(VALID_BUDGET_DESCRIPTION_HOLIDAY).build();
        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editBudgetCommand, model, Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withDescription(VALID_BUDGET_DESCRIPTION_HOLIDAY)
                .withAmount(VALID_BUDGET_AMOUNT_HOLIDAY)
                .withStartDate(VALID_BUDGET_START_DATE_HOLIDAY)
                .withPeriod(VALID_BUDGET_PERIOD_HOLIDAY)
                .build();
        final EditBudgetCommand standardCommand = new EditBudgetCommand(INDEX_FIRST, descriptor);

        // same values -> returns true
        EditBudgetDescriptor copyDescriptor = new EditBudgetDescriptor(descriptor);
        EditBudgetCommand commandWithSameValues = new EditBudgetCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditBudgetCommand(INDEX_SECOND, descriptor)));
    }

}
