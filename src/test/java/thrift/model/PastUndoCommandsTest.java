package thrift.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.AddIncomeCommand;
import thrift.logic.commands.ListCommand;
import thrift.logic.commands.Undoable;
import thrift.logic.commands.exceptions.CommandException;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.IncomeBuilder;

public class PastUndoCommandsTest {

    private PastUndoableCommands pastUndoableCommands = new PastUndoableCommands();

    @Test
    public void getCommandToUndo_undoExpenseCommand_success() throws CommandException {
        Expense validExpense = new ExpenseBuilder().build();
        Undoable addExpenseCommand = new AddExpenseCommand(validExpense);

        pastUndoableCommands.addPastCommand(addExpenseCommand);
        assertEquals(addExpenseCommand, pastUndoableCommands.getCommandToUndo());
        assertTrue(pastUndoableCommands.hasRedoCommand());
        assertFalse(pastUndoableCommands.hasUndoCommand());
    }

    @Test
    public void getCommandToRedo_redoExpenseCommand_success() throws CommandException {

        Expense validExpense = new ExpenseBuilder().build();
        Undoable addExpenseCommand = new AddExpenseCommand(validExpense);
        pastUndoableCommands.addPastCommand(addExpenseCommand);

        //checks if it returns the correct command to undo
        assertEquals(addExpenseCommand, pastUndoableCommands.getCommandToUndo());
        //checks if undone commands is stored in redoStack
        assertTrue(pastUndoableCommands.hasRedoCommand());
        assertFalse(pastUndoableCommands.hasUndoCommand());

        //checks if it returns the correct command to redo
        assertEquals(addExpenseCommand, pastUndoableCommands.getCommandToRedo());
        //checks if actual commands is stored back in undoStack
        assertTrue(pastUndoableCommands.hasUndoCommand());
        assertFalse(pastUndoableCommands.hasRedoCommand());
    }

    @Test
    public void clearRedoStack_undoneCommandsClearedAfterNewUndoableCommandRuns_success() throws CommandException {
        Expense validExpense = new ExpenseBuilder().build();
        Undoable addExpenseCommand = new AddExpenseCommand(validExpense);
        pastUndoableCommands.addPastCommand(addExpenseCommand);

        pastUndoableCommands.getCommandToUndo();
        assertTrue(pastUndoableCommands.hasRedoCommand());

        Income validIncome = new IncomeBuilder().build();
        Undoable addIncomeCommand = new AddIncomeCommand(validIncome);
        pastUndoableCommands.addPastCommand(addIncomeCommand);
        //checks if all the commands in the redo stack are cleared
        assertFalse(pastUndoableCommands.hasRedoCommand());
    }

    @Test
    public void addPastCommand_addListCommandToUndoStack_throwException() {
        assertThrows(ClassCastException.class, () -> pastUndoableCommands.addPastCommand((Undoable) new ListCommand()));
    }

    //other tests for other types of commands
}
