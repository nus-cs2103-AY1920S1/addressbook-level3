package seedu.moolah.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.budget.SwitchBudgetCommand.MESSAGE_BUDGET_ALREADY_PRIMARY;
import static seedu.moolah.logic.commands.budget.SwitchBudgetCommand.MESSAGE_BUDGET_NOT_FOUND;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TypicalMooLah.OUTSIDE_SCHOOL;
import static seedu.moolah.testutil.TypicalMooLah.SCHOOL;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.expense.Description;

public class SwitchBudgetCommandTest {
    @Test
    public void constructor_nullTargetDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SwitchBudgetCommand(null));
    }

    @Test
    public void run_validInput_success() {
        Model model = new ModelManager();
        model.addBudget(SCHOOL);
        model.addBudget(OUTSIDE_SCHOOL);
        assertTrue(model.getPrimaryBudget().isSameBudget(OUTSIDE_SCHOOL)); // primary budget is OUTSIDE_SCHOOL

        try {
            Description validInput = SCHOOL.getDescription();
            SwitchBudgetCommand command = new SwitchBudgetCommand(validInput);
            CommandResult commandResult = command.run(model);
            assertEquals(model.getPrimaryBudget(), SCHOOL);
            assertEquals(String.format(SwitchBudgetCommand.MESSAGE_SUCCESS, validInput),
                    commandResult.getFeedbackToUser());
        } catch (CommandException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void run_budgetAlreadyPrimary_throwsCommandException() {
        Model model = new ModelManager();
        model.addBudget(SCHOOL);
        model.addBudget(OUTSIDE_SCHOOL);
        assertTrue(model.getPrimaryBudget().isSameBudget(OUTSIDE_SCHOOL)); // primary budget is OUTSIDE_SCHOOL

        try {
            SwitchBudgetCommand command = new SwitchBudgetCommand(OUTSIDE_SCHOOL.getDescription());
            command.run(model);
        } catch (CommandException e) {
            assertEquals(MESSAGE_BUDGET_ALREADY_PRIMARY, e.getMessage());
        }
    }

    @Test
    public void run_budgetNotFound_throwsCommandException() {
        Model model = new ModelManager();
        model.addBudget(SCHOOL);
        model.addBudget(OUTSIDE_SCHOOL);
        assertTrue(model.getPrimaryBudget().isSameBudget(OUTSIDE_SCHOOL)); // primary budget is OUTSIDE_SCHOOL

        try {
            SwitchBudgetCommand command = new SwitchBudgetCommand(new Description("nonExistingBudgetName"));
            command.run(model);
        } catch (CommandException e) {
            assertEquals(MESSAGE_BUDGET_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void equals() {
        SwitchBudgetCommand switchToSchoolBudgetCommand = new SwitchBudgetCommand(SCHOOL.getDescription());
        SwitchBudgetCommand switchToOutsideSchoolBudgetCommand = new SwitchBudgetCommand(
                OUTSIDE_SCHOOL.getDescription());

        // same object -> returns true
        assertTrue(switchToSchoolBudgetCommand.equals(switchToSchoolBudgetCommand));

        // same values -> returns true
        SwitchBudgetCommand switchToSchoolBudgetCommandCopy = new SwitchBudgetCommand(SCHOOL.getDescription());
        assertTrue(switchToSchoolBudgetCommand.equals(switchToSchoolBudgetCommandCopy));

        // different types -> returns false
        assertFalse(switchToSchoolBudgetCommand.equals(1));

        // null -> returns false
        assertFalse(switchToSchoolBudgetCommand.equals(null));

        // different description -> returns false
        assertFalse(switchToSchoolBudgetCommand.equals(switchToOutsideSchoolBudgetCommand));
    }

}
