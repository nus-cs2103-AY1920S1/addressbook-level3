package thrift.logic.commands;

import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
