package dukecooks.logic.commands.health;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.health.TypicalRecords.getTypicalHealthRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for GotoHealthCommand.
 */
public class GotoHealthCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHealthRecords(), new UserPrefs());
        expectedModel = new ModelManager(model.getHealthRecords(), new UserPrefs());
    }

    @Test
    public void execute_healthListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new GotoHealthCommand(), model, GotoHealthCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_healthListIsFiltered_showsEverything() {
        model.updateFilteredRecordList(x -> x.getType().equals(CommandTestUtil.VALID_TYPE_CALORIES));
        assertCommandSuccess(new GotoHealthCommand(), model, GotoHealthCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
