package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;

import org.junit.jupiter.api.Test;

import seedu.address.model.*;

public class ClearCommandTest {

    @Test
    public void execute_emptyBankAccount_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitUserState();
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyBankAccount_success() {
        Model model = new ModelManager(getTypicalUserState(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalUserState(), new UserPrefs());
        expectedModel.setUserState(new UserState());
        expectedModel.commitUserState();
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }


}
