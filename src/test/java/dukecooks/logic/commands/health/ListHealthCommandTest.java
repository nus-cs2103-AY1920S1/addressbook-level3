package dukecooks.logic.commands.health;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.health.TypicalRecords.getTypicalHealthRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListHealthCommand.
 */
public class ListHealthCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHealthRecords(), new UserPrefs());
        expectedModel = new ModelManager(model.getHealthRecords(), new UserPrefs());
    }

    @Test
    public void execute_recordListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListHealthCommand(), model, ListHealthCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_recordListIsFiltered_showsEverything() {
        assertCommandSuccess(new ListHealthCommand(), model, ListHealthCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
