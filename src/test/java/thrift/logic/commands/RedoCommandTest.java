package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static thrift.logic.commands.CommandTestUtil.assertCommandFailure;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.PastUndoableCommands;
import thrift.model.UserPrefs;
import thrift.model.transaction.Expense;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.TypicalTransactions;

public class RedoCommandTest {

    private Model model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs(),
            new PastUndoableCommands());

    @Test
    public void execute_noUndoneCommand_throwsCommandException() {
        RedoCommand redoCommand = new RedoCommand();
        assertCommandFailure(redoCommand, model, RedoCommand.NO_REDOABLE_COMMAND);
    }

    @Test
    public void execute_redoAddExpensesCommand_success() throws CommandException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(),
                new PastUndoableCommands());

        UndoCommand undoCommand = new UndoCommand();
        Expense expense = new ExpenseBuilder().build();
        AddExpenseCommand addExpenseCommand = new AddExpenseCommand(expense);

        model.addExpense(expense);
        model.keepTrackCommands(addExpenseCommand);
        assertDoesNotThrow(() -> undoCommand.execute(model));

        RedoCommand redoCommand = new RedoCommand();
        expectedModel.addExpense(expense);
        expectedModel.keepTrackCommands(addExpenseCommand);
        String outputMessage = String.format(AddExpenseCommand.REDO_SUCCESS, expense);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS + "\n" + outputMessage,
                expectedModel);

    }
}
