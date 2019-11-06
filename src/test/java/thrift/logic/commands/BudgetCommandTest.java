package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertRedoCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertUndoCommandSuccess;
import static thrift.testutil.Assert.assertThrows;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.UserPrefs;
import thrift.model.transaction.Budget;
import thrift.model.transaction.BudgetValue;
import thrift.testutil.TypicalTransactions;

public class BudgetCommandTest {

    private Model model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());

    @Test
    public void constructor_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new BudgetCommand(new Budget(null, null)));
    }

    @Test
    public void undoAndRedo_budgetIsNotSetBeforeExecution_success() throws ParseException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs());

        //sets budget
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Budget.BUDGET_DATE_FORMAT.parse("01/2019"));
        BudgetValue budgetValue = new BudgetValue("100");
        Budget budget = new Budget(calendar, budgetValue);
        BudgetCommand budgetCommand = new BudgetCommand(budget);
        String expectedMessage = String.format(BudgetCommand.MESSAGE_SUCCESS, budget);
        Budget oldBudget = expectedModel.setBudget(budget).orElse(null);
        //ensure budget is not set before execution
        assertTrue(oldBudget == null);
        assertCommandSuccess(budgetCommand, model, expectedMessage, expectedModel);

        //undo
        expectedModel.resetBudgetForThatMonth(budget);
        assertUndoCommandSuccess(budgetCommand, model, expectedModel);

        //redo
        expectedModel.setBudget(budget);
        assertRedoCommandSuccess(budgetCommand, model, expectedModel);
    }

    @Test
    public void undoAndRedo_budgetIsSetBeforeExecution_success() throws ParseException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs());

        //sets budget
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Budget.BUDGET_DATE_FORMAT.parse("10/2019"));
        BudgetValue budgetValue = new BudgetValue("100");
        Budget budget = new Budget(calendar, budgetValue);
        BudgetCommand budgetCommand = new BudgetCommand(budget);
        String expectedMessage = String.format(BudgetCommand.MESSAGE_SUCCESS, budget);
        Budget oldBudget = expectedModel.setBudget(budget).orElse(null);
        //ensure that budget is set before
        assertTrue(oldBudget != null);
        assertCommandSuccess(budgetCommand, model, expectedMessage, expectedModel);

        //undo
        expectedModel.setBudget(oldBudget);
        assertUndoCommandSuccess(budgetCommand, model, expectedModel);

        //redo
        expectedModel.setBudget(budget);
        assertRedoCommandSuccess(budgetCommand, model, expectedModel);
    }
}
