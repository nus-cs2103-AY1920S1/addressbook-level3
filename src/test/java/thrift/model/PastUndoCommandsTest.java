package thrift.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.AddIncomeCommand;
import thrift.logic.commands.BudgetCommand;
import thrift.logic.commands.CloneCommand;
import thrift.logic.commands.DeleteCommand;
import thrift.logic.commands.RedoCommand;
import thrift.logic.commands.TagCommand;
import thrift.logic.commands.UndoCommand;
import thrift.logic.commands.Undoable;
import thrift.logic.commands.UntagCommand;
import thrift.logic.commands.UpdateCommand;
import thrift.logic.commands.exceptions.CommandException;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.IncomeBuilder;
import thrift.testutil.TagSetBuilder;
import thrift.testutil.TypicalIndexes;
import thrift.testutil.TypicalOccurrences;
import thrift.testutil.TypicalTransactions;
import thrift.testutil.UpdateTransactionDescriptorBuilder;

public class PastUndoCommandsTest {

    private PastUndoableCommands pastUndoableCommands = new PastUndoableCommands();

    @Test
    public void addPastCommand_addAddExpenseCommand_success() throws CommandException {
        Expense validExpense = new ExpenseBuilder().build();
        Undoable addExpenseCommand = new AddExpenseCommand(validExpense);
        pastUndoableCommands.addPastCommand(addExpenseCommand);
        assertEquals(addExpenseCommand, pastUndoableCommands.getCommandToUndo());
    }

    @Test
    public void addPastCommand_addAddIncomeCommand_success() throws CommandException {
        Income validIncome = new IncomeBuilder().build();
        Undoable addIncomeCommand = new AddIncomeCommand(validIncome);
        pastUndoableCommands.addPastCommand(addIncomeCommand);
        assertEquals(addIncomeCommand, pastUndoableCommands.getCommandToUndo());
    }

    @Test
    public void addPastCommand_addBudgetCommand_success() throws CommandException {
        Undoable budgetCommand = new BudgetCommand(TypicalTransactions.OCT_BUDGET);
        pastUndoableCommands.addPastCommand(budgetCommand);
        assertEquals(budgetCommand, pastUndoableCommands.getCommandToUndo());
    }

    @Test
    public void addPastCommand_addCloneCommand_success() throws CommandException {
        Undoable cloneCommand = new CloneCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                TypicalOccurrences.NO_OCCURRENCE);
        pastUndoableCommands.addPastCommand(cloneCommand);
        assertEquals(cloneCommand, pastUndoableCommands.getCommandToUndo());
    }

    @Test
    public void addPastCommand_addDeleteCommand_success() throws CommandException {
        Undoable deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        pastUndoableCommands.addPastCommand(deleteCommand);
        assertEquals(deleteCommand, pastUndoableCommands.getCommandToUndo());
    }

    @Test
    public void addPastCommand_addTagCommand_success() throws CommandException {
        Undoable tagCommand = new TagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                new TagSetBuilder("Laksa").build());
        pastUndoableCommands.addPastCommand(tagCommand);
        assertEquals(tagCommand, pastUndoableCommands.getCommandToUndo());
    }

    @Test
    public void addPastCommand_addUntagCommand_success() throws CommandException {
        Undoable unTagCommand = new UntagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                new TagSetBuilder("Laksa").build());
        pastUndoableCommands.addPastCommand(unTagCommand);
        assertEquals(unTagCommand, pastUndoableCommands.getCommandToUndo());
    }

    @Test
    public void addPastCommand_addUpdateCommand_success() throws CommandException {
        Undoable updateCommand = new UpdateCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                new UpdateTransactionDescriptorBuilder().withDescription("Chicken").build());
        pastUndoableCommands.addPastCommand(updateCommand);
        assertEquals(updateCommand, pastUndoableCommands.getCommandToUndo());
    }

    @Test
    public void getCommandToUndo_getCommandsFromEmptyUndoStack_throwException() {
        try {
            pastUndoableCommands.getCommandToUndo();
        } catch (CommandException e) {
            assertEquals(UndoCommand.NO_UNDOABLE_COMMAND, e.getMessage());
        }
    }

    @Test
    public void getCommandToUndoAndGetCommandToRedo_undoAndRedoMultipleTimes_orderIsCorrect() throws CommandException {
        Expense validExpense = new ExpenseBuilder().build();
        Undoable addExpenseCommand = new AddExpenseCommand(validExpense);
        pastUndoableCommands.addPastCommand(addExpenseCommand);
        Undoable cloneCommand = new CloneCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION,
                TypicalOccurrences.NO_OCCURRENCE);
        pastUndoableCommands.addPastCommand(cloneCommand);
        // test getCommandToUndo();
        assertEquals(cloneCommand, pastUndoableCommands.getCommandToUndo());
        assertTrue(pastUndoableCommands.hasRedoCommand());
        assertEquals(addExpenseCommand, pastUndoableCommands.getCommandToUndo());
        assertFalse(pastUndoableCommands.hasUndoCommand());
        assertEquals(addExpenseCommand, pastUndoableCommands.getCommandToRedo());
        assertTrue(pastUndoableCommands.hasUndoCommand());
        assertEquals(cloneCommand, pastUndoableCommands.getCommandToRedo());
        assertFalse(pastUndoableCommands.hasRedoCommand());
        assertEquals(cloneCommand, pastUndoableCommands.getCommandToUndo());
        assertEquals(addExpenseCommand, pastUndoableCommands.getCommandToUndo());
    }

    @Test
    public void getCommandToRedo_redoExpenseCommand_success() throws CommandException {
        Expense validExpense = new ExpenseBuilder().build();
        Undoable addExpenseCommand = new AddExpenseCommand(validExpense);
        pastUndoableCommands.addPastCommand(addExpenseCommand);
        assertEquals(addExpenseCommand, pastUndoableCommands.getCommandToUndo());
        assertEquals(addExpenseCommand, pastUndoableCommands.getCommandToRedo());
    }

    @Test
    public void getCommandToRedo_getCommandsFromEmptyRedoStack_throwException() {
        try {
            pastUndoableCommands.getCommandToRedo();
        } catch (CommandException e) {
            assertEquals(RedoCommand.NO_REDOABLE_COMMAND, e.getMessage());
        }
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
}
