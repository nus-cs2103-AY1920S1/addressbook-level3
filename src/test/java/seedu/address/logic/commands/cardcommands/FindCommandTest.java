package seedu.address.logic.commands.cardcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.card.WordContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
class FindCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    void equals() {
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
    void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_CARDS_LISTED_OVERVIEW, 0);
        WordContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCardList());
    }

    @Test
    void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_CARDS_LISTED_OVERVIEW, 2);
        WordContainsKeywordsPredicate predicate = preparePredicate("ee fl");

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code WordContainsKeywordsPredicate}.
     */
    private WordContainsKeywordsPredicate preparePredicate(String userInput) {
        return new WordContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
