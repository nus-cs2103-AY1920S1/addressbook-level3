package seedu.eatme.logic.commands;

import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalEateries.getTypicalReviewEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;

import org.junit.jupiter.api.Test;

import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;

public class StatsCommandTest {
    @Test
    public void execute_hasReviews_success() {
        Model model = new ModelManager(getTypicalReviewEateryList(), getTypicalFeedList(), new UserPrefs());
        assertCommandSuccess(new StatsCommand(true), model, StatsCommand.MESSAGE_STATS_SUCCESS, model);
    }

    @Test
    public void execute_noReviews_throwsCommandException() {
        Model model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());
        assertCommandFailure(new StatsCommand(true), model, StatsCommand.MESSAGE_STATS_ERROR_NODATA);
    }

    @Test
    public void execute_wrongMode_throwsCommandException() {
        Model model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());
        assertCommandFailure(new StatsCommand(false), model, StatsCommand.MESSAGE_STATS_ERROR_WRONGMODE);
    }
}
