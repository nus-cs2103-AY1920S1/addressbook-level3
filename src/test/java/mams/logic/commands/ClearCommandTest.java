package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static mams.testutil.TypicalPersons.getTypicalMams;

import mams.model.Mams;
import org.junit.jupiter.api.Test;

import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyMams_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyMams_success() {
        Model model = new ModelManager(getTypicalMams(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalMams(), new UserPrefs());
        expectedModel.setMams(new Mams());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
