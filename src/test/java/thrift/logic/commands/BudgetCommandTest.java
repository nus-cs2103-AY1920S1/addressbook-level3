package thrift.logic.commands;

import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertRedoCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertUndoCommandSuccess;
import static thrift.testutil.Assert.assertThrows;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.PastUndoableCommands;
import thrift.model.UserPrefs;
import thrift.model.transaction.Budget;
import thrift.model.transaction.BudgetValue;
import thrift.testutil.TypicalTransactions;

public class BudgetCommandTest {

    private Model model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs(),
            new PastUndoableCommands());

    @Test
    public void constructor_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new BudgetCommand(new Budget(null, null)));
    }

    @Test
    public void undo_budgetIsNotSetBeforeExecution_success() throws ParseException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Budget.BUDGET_DATE_FORMAT.parse("11/2019"));
        BudgetValue budgetValue = new BudgetValue("100");
        Budget budget = new Budget(calendar, budgetValue);

        String expectedMessage = String.format(BudgetCommand.MESSAGE_SUCCESS, budget);
        BudgetCommand budgetCommand = new BudgetCommand(budget);
        expectedModel.setBudget(budget);
        assertCommandSuccess(budgetCommand, model, expectedMessage, expectedModel);

        expectedModel.resetBudgetForThatMonth(budget);
        assertUndoCommandSuccess(budgetCommand, model, expectedModel);
    }

    @Test
    public void undo_budgetIsSetBeforeExecution_success() throws ParseException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Budget.BUDGET_DATE_FORMAT.parse("10/2019"));
        BudgetValue budgetValue = new BudgetValue("100");
        Budget budget = new Budget(calendar, budgetValue);

        String expectedMessage = String.format(BudgetCommand.MESSAGE_SUCCESS, budget);
        BudgetCommand budgetCommand = new BudgetCommand(budget);
        Budget oldBudget = expectedModel.setBudget(budget).get();
        assertCommandSuccess(budgetCommand, model, expectedMessage, expectedModel);

        expectedModel.setBudget(oldBudget);
        assertUndoCommandSuccess(budgetCommand, model, expectedModel);
    }

    @Test
    public void redo_budgetIsNotSetBeforeExecution_success() throws ParseException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Budget.BUDGET_DATE_FORMAT.parse("10/2019"));
        BudgetValue budgetValue = new BudgetValue("100");
        Budget budget = new Budget(calendar, budgetValue);

        String expectedMessage = String.format(BudgetCommand.MESSAGE_SUCCESS, budget);
        BudgetCommand budgetCommand = new BudgetCommand(budget);
        Budget oldBudget = expectedModel.setBudget(budget).get();
        assertCommandSuccess(budgetCommand, model, expectedMessage, expectedModel);

        expectedModel.setBudget(oldBudget);
        assertUndoCommandSuccess(budgetCommand, model, expectedModel);

        expectedModel.setBudget(budget);
        assertRedoCommandSuccess(budgetCommand, model, expectedModel);
    }

    @Test
    public void redo_budgetIsSetBeforeExecution_success() throws ParseException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Budget.BUDGET_DATE_FORMAT.parse("10/2019"));
        BudgetValue budgetValue = new BudgetValue("100");
        Budget budget = new Budget(calendar, budgetValue);

        String expectedMessage = String.format(BudgetCommand.MESSAGE_SUCCESS, budget);
        BudgetCommand budgetCommand = new BudgetCommand(budget);
        Budget oldBudget = expectedModel.setBudget(budget).get();
        assertCommandSuccess(budgetCommand, model, expectedMessage, expectedModel);

        expectedModel.setBudget(oldBudget);
        assertUndoCommandSuccess(budgetCommand, model, expectedModel);

        expectedModel.setBudget(budget);
        assertRedoCommandSuccess(budgetCommand, model, expectedModel);
    }
}
