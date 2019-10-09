package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_CARDS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.EEVEE;
import static seedu.address.testutil.TypicalCards.FLAREON;
import static seedu.address.testutil.TypicalCards.getTypicalWordBank;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.WordContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalWordBank(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWordBank(), new UserPrefs());

    @Test
    public void equals() {
        WordContainsKeywordsPredicate firstPredicate =
                new WordContainsKeywordsPredicate(Collections.singletonList("first"));
        WordContainsKeywordsPredicate secondPredicate =
                new WordContainsKeywordsPredicate(Collections.singletonList("second"));

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

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_CARDS_LISTED_OVERVIEW, 0);
        WordContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCardList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_CARDS_LISTED_OVERVIEW, 2);
        WordContainsKeywordsPredicate predicate = preparePredicate("ee fl");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(EEVEE, FLAREON), model.getFilteredCardList());
    }

    /**
     * Parses {@code userInput} into a {@code WordContainsKeywordsPredicate}.
     */
    private WordContainsKeywordsPredicate preparePredicate(String userInput) {
        return new WordContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
