package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.commons.core.Messages.MESSAGE_BOOKMARKS_LISTED_OVERVIEW;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.testutil.TypicalBookmarks.CARL;
import static seedu.mark.testutil.TypicalBookmarks.ELLE;
import static seedu.mark.testutil.TypicalBookmarks.FIONA;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.bookmark.IdentifiersContainKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());

    @Test
    public void equals() {
        IdentifiersContainKeywordsPredicate firstPredicate =
                new IdentifiersContainKeywordsPredicate(Collections.singletonList("first"));
        IdentifiersContainKeywordsPredicate secondPredicate =
                new IdentifiersContainKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different bookmark -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noBookmarkFound() {
        String expectedMessage = String.format(MESSAGE_BOOKMARKS_LISTED_OVERVIEW, 0);
        IdentifiersContainKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredBookmarkList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBookmarkList());
    }

    @Test
    public void execute_multipleKeywords_multipleBookmarksFound() {
        String expectedMessage = String.format(MESSAGE_BOOKMARKS_LISTED_OVERVIEW, 3);
        IdentifiersContainKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredBookmarkList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredBookmarkList());
    }

    /**
     * Parses {@code userInput} into a {@code IdentifiersContainKeywordsPredicate}.
     */
    private IdentifiersContainKeywordsPredicate preparePredicate(String userInput) {
        return new IdentifiersContainKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
