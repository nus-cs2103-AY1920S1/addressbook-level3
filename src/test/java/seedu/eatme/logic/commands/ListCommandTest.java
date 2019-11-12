package seedu.eatme.logic.commands;

import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.logic.commands.CommandTestUtil.showEateryAtIndex;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_FIRST_EATERY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());
        expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
