package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProblems.getTypicalAlgoBase;

import org.junit.jupiter.api.Test;

import seedu.address.model.AlgoBase;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAlgoBase_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAlgoBase_success() {
        Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
        expectedModel.setAlgoBase(new AlgoBase());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
