package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showEateryAtIndex;
import static seedu.address.testutil.TypicalEateries.getTypicalOpenAddressBook;
import static seedu.address.testutil.TypicalFeeds.getTypicalFeedList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EATERY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EATERY;
import static seedu.address.testutil.TypicalReviews.REVIEW_1;
import static seedu.address.testutil.TypicalReviews.REVIEW_2;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ReviewCommandTest {

    private Model model = new ModelManager(getTypicalOpenAddressBook(), getTypicalFeedList(), new UserPrefs());

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
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEateryList().size());

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
