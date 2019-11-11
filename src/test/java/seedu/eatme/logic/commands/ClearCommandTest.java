package seedu.eatme.logic.commands;

import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;

import org.junit.jupiter.api.Test;

import seedu.eatme.model.EateryList;
import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyEateryList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyEateryList_success() {
        Model model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());
        expectedModel.setEateryList(new EateryList());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
