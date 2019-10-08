package thrift.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.ListCommand;
import thrift.logic.commands.Undoable;
import thrift.model.transaction.Expense;
import thrift.testutil.ExpenseBuilder;

public class PastUndoCommandsTest {

    private PastUndoableCommands pastUndoableCommands = new PastUndoableCommands();

    @Test
    public void addPastCommand_addExpenseCommandToUndoStack_success() {
        Expense validExpense = new ExpenseBuilder().build();
        Undoable addExpenseCommand = new AddExpenseCommand(validExpense);

        pastUndoableCommands.addPastCommand(addExpenseCommand);
        assertEquals(addExpenseCommand, pastUndoableCommands.getCommandToUndo());
    }

    @Test
    public void addPastCommand_addListCommandToUndoStack_throwException() {
        assertThrows(ClassCastException.class, () -> pastUndoableCommands.addPastCommand((Undoable) new ListCommand()));
    }

    //other tests for other types of commands
}
