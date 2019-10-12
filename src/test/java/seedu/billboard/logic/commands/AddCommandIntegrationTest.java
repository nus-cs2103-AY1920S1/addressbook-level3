package seedu.billboard.logic.commands;

import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBillboard(), new UserPrefs());
    }

    @Test
    public void execute_newExpense_success() {
        Expense validExpense = new ExpenseBuilder().build();

        Model expectedModel = new ModelManager(model.getBillboardExpenses(), new UserPrefs());
        expectedModel.addExpense(validExpense);

        assertCommandSuccess(new AddCommand(validExpense), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validExpense), expectedModel);
    }

    @Test
    public void execute_duplicateExpense_throwsCommandException() {
        Expense expenseInList = model.getBillboardExpenses().getExpenses().get(0);
        assertCommandFailure(new AddCommand(expenseInList), model, AddCommand.MESSAGE_DUPLICATE_EXPENSE);
    }

}
