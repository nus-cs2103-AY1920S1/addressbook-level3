package seedu.billboard.logic.commands;

import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import java.util.List;
import java.util.stream.Collectors;

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
        List<String> tagNames = validExpense.getTags().stream().map(x -> x.tagName).collect(Collectors.toList());


        Model expectedModel = new ModelManager(model.getBillboard(), new UserPrefs());
        expectedModel.addExpense(validExpense);

        assertCommandSuccess(new AddCommand(validExpense.getName(), validExpense.getDescription(),
                        validExpense.getAmount(), validExpense.getCreated(), tagNames), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validExpense), expectedModel);
    }

    @Test
    public void execute_duplicateExpense_throwsCommandException() {
        Expense expenseInList = model.getBillboard().getExpenses().get(0);
        List<String> tagNames = expenseInList.getTags().stream().map(x -> x.tagName).collect(Collectors.toList());

        assertCommandFailure(new AddCommand(expenseInList.getName(), expenseInList.getDescription(),
                expenseInList.getAmount(), expenseInList.getCreated(), tagNames),
                model, AddCommand.MESSAGE_DUPLICATE_EXPENSE);
    }

}
