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
        model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());
    }

    @Test
    public void execute_newIncome_success() {
        Income validIncome = new IncomeBuilder().build();

        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs());
        expectedModel.addIncome(validIncome);

        assertCommandSuccess(new AddIncomeCommand(validIncome), model,
                String.format(AddIncomeCommand.MESSAGE_SUCCESS, validIncome), expectedModel);
    }

    @Test
    public void undoAndRedo_addIncome_success() throws CommandException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs());

        //adds income
        Income validIncome = new IncomeBuilder().build();
        model.addIncome(validIncome);
        AddIncomeCommand addIncomeCommand = new AddIncomeCommand(validIncome);
        model.keepTrackCommands(addIncomeCommand);
        expectedModel.addIncome(validIncome);
        assertEquals(expectedModel, model);

        //undo
        Undoable undoable = model.getPreviousUndoableCommand();
        expectedModel.deleteLastTransaction();
        assertUndoCommandSuccess(undoable, model, expectedModel);

        //redo
        undoable = model.getUndoneCommand();
        expectedModel.addIncome(validIncome);
        assertRedoCommandSuccess(undoable, model, expectedModel);
    }
}
