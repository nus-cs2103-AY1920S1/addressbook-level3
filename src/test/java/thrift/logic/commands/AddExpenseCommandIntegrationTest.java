package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.PastUndoableCommands;
import thrift.model.UserPrefs;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Transaction;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.TypicalTransactions;

/**
 * Contains integration tests (interaction with the Model) for {@code AddExpenseCommand}.
 */
public class AddExpenseCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs(), new PastUndoableCommands());
    }

    @Test
    public void execute_newExpense_success() {
        Expense validExpense = new ExpenseBuilder().build();

        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());
        expectedModel.addExpense(validExpense);

        assertCommandSuccess(new AddExpenseCommand(validExpense), model,
                String.format(AddExpenseCommand.MESSAGE_SUCCESS, validExpense), expectedModel);
    }

    @Test
    public void undo_undoAddExpense_success() throws CommandException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());

        Expense validExpense = new ExpenseBuilder().build();
        model.addExpense(validExpense);
        AddExpenseCommand addExpenseCommand = new AddExpenseCommand(validExpense);
        model.keepTrackCommands(addExpenseCommand);
        expectedModel.addExpense(validExpense);
        assertEquals(expectedModel, model);

        Undoable undoable = model.getPreviousUndoableCommand();
        undoable.undo(model);
        Transaction transactionToDelete = expectedModel.getLastTransactionFromThrift();
        expectedModel.deleteTransaction(transactionToDelete);
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo_redoAddExpense_success() throws CommandException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());

        //add expense to THIRFT
        Expense validExpense = new ExpenseBuilder().build();
        model.addExpense(validExpense);
        AddExpenseCommand addExpenseCommand = new AddExpenseCommand(validExpense);
        model.keepTrackCommands(addExpenseCommand);
        expectedModel.addExpense(validExpense);
        assertEquals(expectedModel, model);

        //undo add_expense command
        Undoable undoable = model.getPreviousUndoableCommand();
        undoable.undo(model);
        Transaction transactionToDelete = expectedModel.getLastTransactionFromThrift();
        expectedModel.deleteTransaction(transactionToDelete);
        assertEquals(expectedModel, model);

        //redo add_expense command
        undoable = model.getUndoneCommand();
        undoable.redo(model);
        expectedModel.addExpense(validExpense);
        assertEquals(expectedModel, model);
    }

}
