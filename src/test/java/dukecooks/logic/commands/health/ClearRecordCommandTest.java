package dukecooks.logic.commands.health;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.health.TypicalRecords.getTypicalHealthRecords;

import org.junit.jupiter.api.Test;

import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.health.HealthRecords;

public class ClearRecordCommandTest {

    @Test
    public void execute_emptyHealthRecords_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearRecordCommand(), model, ClearRecordCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyHealthRecords_success() {
        Model model = new ModelManager(getTypicalHealthRecords(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalHealthRecords(), new UserPrefs());
        expectedModel.setHealthRecords(new HealthRecords());

        assertCommandSuccess(new ClearRecordCommand(), model, ClearRecordCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
