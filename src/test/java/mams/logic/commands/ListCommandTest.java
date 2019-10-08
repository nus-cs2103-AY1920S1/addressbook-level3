package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static mams.logic.commands.CommandTestUtil.showStudentAtIndex;
import static mams.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static mams.testutil.TypicalStudents.getTypicalMams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMams(), new UserPrefs());
        expectedModel = new ModelManager(model.getMams(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
