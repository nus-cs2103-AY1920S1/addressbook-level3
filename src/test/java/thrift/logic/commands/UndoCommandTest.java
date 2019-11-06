package thrift.logic.commands;

import static thrift.logic.commands.CommandTestUtil.assertCommandFailure;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.UserPrefs;
import thrift.model.transaction.Expense;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.TypicalTransactions;

public class UndoCommandTest {

    private Model model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());

    @Test
    public void execute_noUndoableCommand_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();
        assertCommandFailure(undoCommand, model, UndoCommand.NO_UNDOABLE_COMMAND);
    }

    @Test
    public void execute_undoAddExpensesCommand_success() {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs());

        //adds expense
        Expense expense = new ExpenseBuilder().build();
        AddExpenseCommand addExpenseCommand = new AddExpenseCommand(expense);
        model.addExpense(expense);
        model.keepTrackCommands(addExpenseCommand);

        //undo
        UndoCommand undoCommand = new UndoCommand();
        String outputMessage = String.format(AddExpenseCommand.UNDO_SUCCESS, expense);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS + "\n" + outputMessage,
                expectedModel);
    }
}
