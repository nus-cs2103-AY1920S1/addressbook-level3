package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalIncidentManager;

import org.junit.jupiter.api.Test;

import seedu.address.model.IncidentManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyIncidentManager_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyIncidentManager_success() {
        Model model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
        expectedModel.setIncidentManager(new IncidentManager());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
