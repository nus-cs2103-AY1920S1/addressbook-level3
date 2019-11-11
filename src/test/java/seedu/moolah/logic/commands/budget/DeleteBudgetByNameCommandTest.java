package seedu.moolah.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_DESCRIPTION_HOLIDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_DESCRIPTION_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelHistory;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Description;

public class DeleteBudgetByNameCommandTest {
    private static final Description SCHOOL = new Description(VALID_BUDGET_DESCRIPTION_SCHOOL);
    private static final Description HOLIDAY = new Description(VALID_BUDGET_DESCRIPTION_HOLIDAY);

    private Model model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());

    @Test
    public void run_validName_success() {
        Budget budgetToDelete = model.getPrimaryBudget();
        DeleteBudgetByNameCommand deleteBudgetByNameCommand = new DeleteBudgetByNameCommand(SCHOOL);

        String expectedMessage = String.format(DeleteBudgetByNameCommand.MESSAGE_DELETE_BUDGET_SUCCESS, SCHOOL);

        ModelManager expectedModel = new ModelManager(model.getMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel.commitModel("Delete budget with name school related expenses");
        expectedModel.deleteBudget(budgetToDelete);

        assertCommandSuccess(deleteBudgetByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_invalidName_throwsCommandException() {
        Description invalidName = new Description("xxxx");
        DeleteBudgetByNameCommand deleteBudgetByNameCommand = new DeleteBudgetByNameCommand(invalidName);

        assertCommandFailure(deleteBudgetByNameCommand, model, Messages.MESSAGE_BUDGET_NOT_FOUND);
    }

    @Test
    public void run_defaultBudgetName_throwsCommandException() {
        Description defaultBudgetName = Budget.DEFAULT_BUDGET_DESCRIPTION;
        DeleteBudgetByNameCommand deleteBudgetByNameCommand = new DeleteBudgetByNameCommand(defaultBudgetName);

        assertCommandFailure(deleteBudgetByNameCommand, model, Messages.MESSAGE_CANNOT_DELETE_DEFAULT_BUDGET);
    }

    @Test
    public void equals() {
        DeleteBudgetByNameCommand deleteSchoolCommand = new DeleteBudgetByNameCommand(SCHOOL);
        DeleteBudgetByNameCommand deleteOutsideScholCommand = new DeleteBudgetByNameCommand(HOLIDAY);

        // same object -> returns true
        assertTrue(deleteSchoolCommand.equals(deleteSchoolCommand));

        // same values -> returns true
        DeleteBudgetByNameCommand deleteSchoolCommandCopy = new DeleteBudgetByNameCommand(SCHOOL);
        assertTrue(deleteSchoolCommand.equals(deleteSchoolCommandCopy));

        // different types -> returns false
        assertFalse(deleteSchoolCommand.equals(1));

        // null -> returns false
        assertFalse(deleteSchoolCommand.equals(null));

        // different description -> returns false
        assertFalse(deleteSchoolCommand.equals(deleteOutsideScholCommand));
    }

}

