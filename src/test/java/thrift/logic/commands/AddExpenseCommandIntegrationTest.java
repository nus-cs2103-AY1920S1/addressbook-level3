package thrift.logic.commands;

import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.UserPrefs;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.IncomeBuilder;
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
    public void execute_newIncome_success() {
        Income validIncome = new IncomeBuilder().build();

        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs());
        expectedModel.addIncome(validIncome);

        assertCommandSuccess(new AddIncomeCommand(validIncome), model,
                String.format(AddIncomeCommand.MESSAGE_SUCCESS, validIncome), expectedModel);
    }

}
