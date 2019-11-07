package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTutorAid.getTypicalTutorAid;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TutorAid;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyTutorAid_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitTutorAid();
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTutorAid_success() {
        Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTutorAid(), new UserPrefs());
        expectedModel.setVersionedTutorAid(new TutorAid());
        expectedModel.commitTutorAid();
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
