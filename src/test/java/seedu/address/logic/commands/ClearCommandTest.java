package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;

import org.junit.jupiter.api.Test;

import seedu.address.model.ExpenseList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyExpenseList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyExpenseList_success() {
        Model model = new ModelManager(getTypicalExpenseList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalExpenseList(), new UserPrefs());
        expectedModel.setExpenseList(new ExpenseList());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
