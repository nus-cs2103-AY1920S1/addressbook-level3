package seedu.billboard.logic.commands;

import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import org.junit.jupiter.api.Test;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyBillboard_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyBillboard_success() {
        Model model = new ModelManager(getTypicalBillboard(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalBillboard(), new UserPrefs());
        expectedModel.setBillboard(new Billboard());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
