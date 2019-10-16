package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_FLASHCARD_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashCards.DELAY;
import static seedu.address.testutil.TypicalFlashCards.STORE_AND_FORWARD;
import static seedu.address.testutil.TypicalFlashCards.THROUGHPUT;
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.AnswerContainsAnyKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindAnswerCommand}.
 */
public class FindAnswerCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        AnswerContainsAnyKeywordsPredicate firstPredicate =
                new AnswerContainsAnyKeywordsPredicate(Collections.singletonList("first"));
        AnswerContainsAnyKeywordsPredicate secondPredicate =
                new AnswerContainsAnyKeywordsPredicate(Collections.singletonList("second"));

        FindAnswerCommand findFirstCommand = new FindAnswerCommand(firstPredicate);
        FindAnswerCommand findSecondCommand = new FindAnswerCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindAnswerCommand findFirstCommandCopy = new FindAnswerCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different flashCard -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noFlashCardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARD_LISTED_OVERVIEW, 0);
        AnswerContainsAnyKeywordsPredicate predicate = preparePredicate(" ");
        FindAnswerCommand command = new FindAnswerCommand(predicate);
        expectedModel.updateFilteredFlashCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashCardList());
    }

    @Test
    public void execute_multipleKeywords_multipleFlashCardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARD_LISTED_OVERVIEW, 3);
        AnswerContainsAnyKeywordsPredicate predicate = preparePredicate("transmitted other");
        FindAnswerCommand command = new FindAnswerCommand(predicate);
        expectedModel.updateFilteredFlashCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(STORE_AND_FORWARD, DELAY, THROUGHPUT), model.getFilteredFlashCardList());
    }

    /**
     * Parses {@code userInput} into a {@code AnswerContainsAnyKeywordsPredicate}.
     */
    private AnswerContainsAnyKeywordsPredicate preparePredicate(String userInput) {
        return new AnswerContainsAnyKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
