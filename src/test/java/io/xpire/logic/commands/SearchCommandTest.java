package io.xpire.logic.commands;

import static io.xpire.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.testutil.TypicalItems.BANANA;
import static io.xpire.testutil.TypicalItems.CORN;
import static io.xpire.testutil.TypicalItems.getTypicalExpiryDateTracker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.ContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalExpiryDateTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalExpiryDateTracker(), new UserPrefs());

    @Test
    public void equals() {
        ContainsKeywordsPredicate firstPredicate =
                new ContainsKeywordsPredicate(Collections.singletonList("first"));
        ContainsKeywordsPredicate secondPredicate =
                new ContainsKeywordsPredicate(Collections.singletonList("second"));

        SearchCommand findFirstCommand = new SearchCommand(firstPredicate);
        SearchCommand findSecondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        SearchCommand findFirstCommandCopy = new SearchCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 0);
        ContainsKeywordsPredicate predicate = preparePredicate(" ");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredItemList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 2);
        ContainsKeywordsPredicate predicate = preparePredicate("Banana Corn");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BANANA, CORN), model.getFilteredItemList());
    }

    /**
     * Parses {@code userInput} into a {@code ContainsKeywordsPredicate}.
     */
    private ContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
