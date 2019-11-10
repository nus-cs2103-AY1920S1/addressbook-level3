package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEateries.getTypicalOpenAddressBook;
import static seedu.address.testutil.TypicalEateries.getTypicalReviewAddressBook;
import static seedu.address.testutil.TypicalFeeds.getTypicalFeedList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class StatsCommandTest {
    @Test
    public void execute_hasReviews_success() {
        Model model = new ModelManager(getTypicalReviewAddressBook(), getTypicalFeedList(), new UserPrefs());
        assertCommandSuccess(new StatsCommand(true), model, StatsCommand.MESSAGE_STATS_SUCCESS, model);
    }

    @Test
    public void execute_noReviews_throwsCommandException() {
        Model model = new ModelManager(getTypicalOpenAddressBook(), getTypicalFeedList(), new UserPrefs());
        assertCommandFailure(new StatsCommand(true), model, StatsCommand.MESSAGE_STATS_ERROR_NODATA);
    }

    @Test
    public void execute_wrongMode_throwsCommandException() {
        Model model = new ModelManager(getTypicalOpenAddressBook(), getTypicalFeedList(), new UserPrefs());
        assertCommandFailure(new StatsCommand(false), model, StatsCommand.MESSAGE_STATS_ERROR_WRONGMODE);
    }
}
