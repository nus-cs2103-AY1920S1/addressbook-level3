package seedu.address.logic.commands.event;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListEventsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(model.getMooLah(), new UserPrefs(), new ModelHistory());
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
