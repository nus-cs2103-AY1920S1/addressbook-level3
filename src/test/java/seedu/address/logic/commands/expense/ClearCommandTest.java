package seedu.address.logic.commands.expense;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.getTypicalMooLah;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.MooLah;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void run_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitModel("");

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void run_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        Model expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel.commitModel("");
        expectedModel.setMooLah(new MooLah());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
