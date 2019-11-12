package seedu.eatme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eatme.logic.commands.CommandTestUtil.showEateryAtIndex;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_FIRST_EATERY;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_SECOND_EATERY;
import static seedu.eatme.testutil.TypicalReviews.REVIEW_1;
import static seedu.eatme.testutil.TypicalReviews.REVIEW_2;

import org.junit.jupiter.api.Test;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;

public class ReviewCommandTest {

    private Model model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEateryList().size() + 1);
        ReviewCommand reviewCommand = new ReviewCommand(outOfBoundIndex, REVIEW_1);

        assertCommandFailure(reviewCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);

        Index outOfBoundIndex = INDEX_SECOND_EATERY;
        // ensures that outOfBoundIndex is still in bounds of eatery list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEateryList().getEateryList().size());

        ReviewCommand reviewCommand = new ReviewCommand(outOfBoundIndex, REVIEW_2);
        assertCommandFailure(reviewCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ReviewCommand reviewFirst = new ReviewCommand(INDEX_FIRST_EATERY, REVIEW_1);
        ReviewCommand reviewSecond = new ReviewCommand(INDEX_SECOND_EATERY, REVIEW_1);

        //same object return true
        assertTrue(reviewFirst.equals(reviewFirst));

        //null returns false
        assertFalse(reviewFirst.equals(null));

        //different type returns false
        assertFalse(reviewFirst.equals(1));

        //different eatery returns false
        assertFalse(reviewFirst.equals(reviewSecond));
    }
}
