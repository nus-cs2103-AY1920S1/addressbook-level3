package seedu.moolah.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_DESCRIPTION_HOLIDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_DESCRIPTION_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;

public class DeleteBudgetByNameCommandTest {
    private static final Description SCHOOL = new Description(VALID_BUDGET_DESCRIPTION_SCHOOL);
    private static final Description HOLIDAY = new Description(VALID_BUDGET_DESCRIPTION_HOLIDAY);

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_validName_success() {
        Budget budgetToDelete = model.getPrimaryBudget();
        DeleteBudgetByNameCommand command = new DeleteBudgetByNameCommand(SCHOOL);

        expectedModel.deleteBudget(budgetToDelete);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()).setMooLah(model.getMooLah()));

        String expectedMessage = String.format(DeleteBudgetByNameCommand.MESSAGE_DELETE_BUDGET_SUCCESS, SCHOOL);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
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
        DeleteBudgetByNameCommand deleteOutsideSchoolCommand = new DeleteBudgetByNameCommand(HOLIDAY);

        // same object -> returns true
        assertEquals(deleteSchoolCommand, deleteSchoolCommand);

        // same values -> returns true
        DeleteBudgetByNameCommand deleteSchoolCommandCopy = new DeleteBudgetByNameCommand(SCHOOL);
        assertEquals(deleteSchoolCommand, deleteSchoolCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteSchoolCommand);

        // null -> returns false
        assertNotEquals(null, deleteSchoolCommand);

        // different expense -> returns false
        assertNotEquals(deleteSchoolCommand, deleteOutsideSchoolCommand);
    }

}

