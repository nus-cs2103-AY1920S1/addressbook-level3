package dukecooks.logic.commands.diary;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for CreateDiaryCommand.
 */
public class AddPageCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());
        expectedModel = new ModelManager(model.getDiaryRecords(), new UserPrefs());
    }

    @Test
    public void execute_addPage() {
        assertCommandSuccess(new AddPageCommand(), model, AddPageCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
