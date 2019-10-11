package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_FLASHCARD_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashCards.PROTOCOL;
import static seedu.address.testutil.TypicalFlashCards.SOURCE_DELAY;
import static seedu.address.testutil.TypicalFlashCards.THROUGHPUT;
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.QuestionOrAnswerContainsAnyKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        QuestionOrAnswerContainsAnyKeywordsPredicate firstPredicate =
                new QuestionOrAnswerContainsAnyKeywordsPredicate(Collections.singletonList("first"));
        QuestionOrAnswerContainsAnyKeywordsPredicate secondPredicate =
                new QuestionOrAnswerContainsAnyKeywordsPredicate(Collections.singletonList("second"));

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

        // different flashCard -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noFlashCardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARD_LISTED_OVERVIEW, 0);
        QuestionOrAnswerContainsAnyKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashCardList());
    }

    @Test
    public void execute_multipleKeywords_multipleFlashCardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARD_LISTED_OVERVIEW, 3);
        QuestionOrAnswerContainsAnyKeywordsPredicate predicate = preparePredicate("sources protocol throughput");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(SOURCE_DELAY, THROUGHPUT, PROTOCOL), model.getFilteredFlashCardList());
    }

    /**
     * Parses {@code userInput} into a {@code QuestionContainsAnyKeywordsPredicate}.
     */
    private QuestionOrAnswerContainsAnyKeywordsPredicate preparePredicate(String userInput) {
        return new QuestionOrAnswerContainsAnyKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
