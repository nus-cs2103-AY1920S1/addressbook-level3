package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertRedoCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertUndoCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.UserPrefs;
import thrift.model.transaction.Expense;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.TypicalTransactions;

/**
 * Contains integration tests (interaction with the Model) for {@code AddExpenseCommand}.
 */
public class AddExpenseCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());
    }

    @Test
    public void execute_newExpense_success() {
        Expense validExpense = new ExpenseBuilder().build();

        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs());
        expectedModel.addExpense(validExpense);

        assertCommandSuccess(new AddExpenseCommand(validExpense), model,
                String.format(AddExpenseCommand.MESSAGE_SUCCESS, validExpense), expectedModel);
    }

    @Test
    public void undoAndRedo_addExpense_success() throws CommandException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs());

        //adds expense
        Expense validExpense = new ExpenseBuilder().build();
        model.addExpense(validExpense);
        AddExpenseCommand addExpenseCommand = new AddExpenseCommand(validExpense);
        model.keepTrackCommands(addExpenseCommand);
        expectedModel.addExpense(validExpense);
        assertEquals(expectedModel, model);

        //undo
        Undoable undoable = model.getPreviousUndoableCommand();
        expectedModel.deleteLastTransaction();
        assertUndoCommandSuccess(undoable, model, expectedModel);

        //redo
        undoable = model.getUndoneCommand();
        expectedModel.addExpense(validExpense);
        assertRedoCommandSuccess(undoable, model, expectedModel);
    }

}
