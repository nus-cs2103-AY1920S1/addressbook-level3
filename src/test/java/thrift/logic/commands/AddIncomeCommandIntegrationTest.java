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
import thrift.model.transaction.Income;
import thrift.testutil.IncomeBuilder;
import thrift.testutil.TypicalTransactions;

/**
 * Contains integration tests (interaction with the Model) for {@code AddIncomeCommand}
 */
public class AddIncomeCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs(), new PastUndoableCommands());
    }

    @Test
    public void execute_newIncome_success() {
        Income validIncome = new IncomeBuilder().build();

        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());
        expectedModel.addIncome(validIncome);

        assertCommandSuccess(new AddIncomeCommand(validIncome), model,
                String.format(AddIncomeCommand.MESSAGE_SUCCESS, validIncome), expectedModel);
    }

    @Test
    public void undo_success() {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());

        Income validIncome = new IncomeBuilder().build();

        model.addIncome(validIncome);
        AddIncomeCommand addIncomeCommand = new AddIncomeCommand(validIncome);
        model.keepTrackCommands(addIncomeCommand);
        expectedModel.addIncome(validIncome);
        assertEquals(expectedModel, model);

        Undoable undoable = model.getPreviousUndoableCommand();
        undoable.undo(model);
        expectedModel.deleteLastTransaction();
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo_redoAddIncome_success() throws CommandException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(), new PastUndoableCommands());

        //add income to THIRFT
        Income validIncome = new IncomeBuilder().build();
        model.addIncome(validIncome);
        AddIncomeCommand addIncomeCommand = new AddIncomeCommand(validIncome);
        model.keepTrackCommands(addIncomeCommand);
        expectedModel.addIncome(validIncome);
        assertEquals(expectedModel, model);

        //undo add_income command
        Undoable undoable = model.getPreviousUndoableCommand();
        undoable.undo(model);
        expectedModel.deleteLastTransaction();
        assertEquals(expectedModel, model);

        //redo add_income command
        undoable = model.getUndoneCommand();
        undoable.redo(model);
        expectedModel.addIncome(validIncome);
        assertEquals(expectedModel, model);
    }
}
