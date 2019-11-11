package seedu.moolah.logic.commands.event;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.modelhistory.ModelHistory;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListEventsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListEventsCommand(), model, ListEventsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    //    @Test
    //    public void run_listIsFiltered_showsEverything() {
    //        showEventAtIndex(model, INDEX_FIRST_EVENT);
    //        expectedModel.setModelHistory(new ModelHistory("", makeModelStack(model), makeModelStack()));
    //        assertCommandSuccess(new ListEventsCommand(), model, ListEventsCommand.MESSAGE_SUCCESS, expectedModel);
    //    }
}
