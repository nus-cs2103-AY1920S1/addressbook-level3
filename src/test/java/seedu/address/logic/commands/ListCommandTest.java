package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.logic.commands.CommandTestUtil.showStudyPlanAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDYPLAN;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    //TODO MODIFY TESTS

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        //model = new ModelManager(getTypicalModulePlanner(), new UserPrefs());
        //expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs());
    }

    /*
    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showStudyPlanAtIndex(model, INDEX_FIRST_STUDYPLAN);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
     */
}
