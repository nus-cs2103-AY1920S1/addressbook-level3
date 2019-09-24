package seedu.algobase.logic.commands;

import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.algobase.testutil.TypicalProblems.getTypicalAlgoBase;

import org.junit.jupiter.api.Test;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;

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
