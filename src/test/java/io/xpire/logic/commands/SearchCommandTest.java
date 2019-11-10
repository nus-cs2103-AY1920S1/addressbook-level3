package io.xpire.logic.commands;

import static io.xpire.commons.core.Messages.MESSAGE_EMPTY_LIST;
import static io.xpire.commons.core.Messages.MESSAGE_SUGGESTIONS;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.SearchCommand.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalItems.BANANA;
import static io.xpire.testutil.TypicalItems.DUCK;
import static io.xpire.testutil.TypicalItems.FISH;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import io.xpire.logic.parser.Parser;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.ContainsKeywordsPredicate;

//@@author JermyTan
/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalLists(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());

    @Test
    public void equals() {
        ContainsKeywordsPredicate firstPredicate =
                new ContainsKeywordsPredicate(Collections.singletonList("first"));
        ContainsKeywordsPredicate secondPredicate =
                new ContainsKeywordsPredicate(Collections.singletonList("second"));

        SearchCommand findFirstCommand = new SearchCommand(XPIRE, firstPredicate);
        SearchCommand findSecondCommand = new SearchCommand(XPIRE, secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        SearchCommand findFirstCommandCopy = new SearchCommand(XPIRE, firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different xpireItem -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_emptyXpire_failure() {
        Model model = new ModelManager();

        assertCommandFailure(new SearchCommand(XPIRE, preparePredicate("test")), model, MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_emptyReplenishList_failure() {
        Model model = new ModelManager();

        assertCommandFailure(new SearchCommand(REPLENISH, preparePredicate("test")), model, MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_noMatchingKeywords_noItemsFoundNoRecommendations() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 0);
        ContainsKeywordsPredicate predicate = preparePredicate("Pineapple|Pear|#Cold");
        SearchCommand command = new SearchCommand(XPIRE, predicate);
        expectedModel.filterCurrentList(XPIRE, predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getCurrentList());
    }

    @Test
    public void execute_noMatchingKeywords_noItemsFoundWithRecommendations() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 0)
                + String.format(MESSAGE_SUGGESTIONS, "[Banana]");
        ContainsKeywordsPredicate predicate = preparePredicate("Banaan");
        SearchCommand command = new SearchCommand(XPIRE, predicate);
        expectedModel.filterCurrentList(XPIRE, predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getCurrentList());
    }

    @Test
    public void execute_allMatchingKeywords_someItemsFoundNoRecommendations() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 2);
        ContainsKeywordsPredicate predicate = preparePredicate("Banaan|#Fridge");
        SearchCommand command = new SearchCommand(XPIRE, predicate);
        expectedModel.filterCurrentList(XPIRE, predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DUCK, FISH), model.getCurrentList());
    }

    @Test
    public void execute_allMatchingKeywords_someItemsFoundWithRecommendations() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 0)
                + String.format(MESSAGE_SUGGESTIONS, "[#Fridge]")
                + String.format(MESSAGE_SUGGESTIONS, "[Banana]");
        ContainsKeywordsPredicate predicate = preparePredicate("Banaan|#Fridg");
        SearchCommand command = new SearchCommand(XPIRE, predicate);
        expectedModel.filterCurrentList(XPIRE, predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getCurrentList());
    }

    @Test
    public void execute_allMatchingKeywords_multipleItemsFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 3);
        ContainsKeywordsPredicate predicate = preparePredicate("Banana|#Fridge");
        SearchCommand command = new SearchCommand(XPIRE, predicate);
        expectedModel.filterCurrentList(XPIRE, predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BANANA, DUCK, FISH), model.getCurrentList());
    }

    @Test
    public void execute_someMatchingKeywords_multipleItemsFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 2);
        ContainsKeywordsPredicate predicate = preparePredicate("Pineapple|Banana|#Protein|#Cold");
        SearchCommand command = new SearchCommand(XPIRE, predicate);
        expectedModel.filterCurrentList(XPIRE, predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BANANA, DUCK), model.getCurrentList());
    }

    /**
     * Parses {@code userInput} into a {@code ContainsKeywordsPredicate}.
     */
    private ContainsKeywordsPredicate preparePredicate(String parsedUserInput) {
        return new ContainsKeywordsPredicate(Arrays.asList(parsedUserInput.split(Parser.SEPARATOR)));
    }
}
