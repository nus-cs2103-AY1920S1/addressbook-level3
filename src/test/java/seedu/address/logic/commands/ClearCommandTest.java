package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ClearCommandTest {


    @Test
    public void execute_emptyClassroom_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }


    /*
    @Test
    public void execute_nonEmptyClassroom_success() {
        Model model = new ModelManager(getTypicalNotebook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalNotebook(), new UserPrefs());
        expectedModel.setClassroom(new Classroom());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
    */

}
