package seedu.moolah.logic.commands.general;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelHistory;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.MooLah;
import seedu.moolah.model.UserPrefs;

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
