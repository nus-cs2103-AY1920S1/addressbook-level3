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
import seedu.address.model.flashcard.QuestionContainsAnyKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindQuestionCommand}.
 */
public class FindQuestionCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        QuestionContainsAnyKeywordsPredicate firstPredicate =
                new QuestionContainsAnyKeywordsPredicate(Collections.singletonList("first"));
        QuestionContainsAnyKeywordsPredicate secondPredicate =
                new QuestionContainsAnyKeywordsPredicate(Collections.singletonList("second"));

        FindQuestionCommand findFirstCommand = new FindQuestionCommand(firstPredicate);
        FindQuestionCommand findSecondCommand = new FindQuestionCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindQuestionCommand findFirstCommandCopy = new FindQuestionCommand(firstPredicate);
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
        QuestionContainsAnyKeywordsPredicate predicate = preparePredicate(" ");
        FindQuestionCommand command = new FindQuestionCommand(predicate);
        expectedModel.updateFilteredFlashCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashCardList());
    }

    @Test
    public void execute_multipleKeywords_multipleFlashCardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARD_LISTED_OVERVIEW, 3);
        QuestionContainsAnyKeywordsPredicate predicate = preparePredicate("sources protocol throughput");
        FindQuestionCommand command = new FindQuestionCommand(predicate);
        expectedModel.updateFilteredFlashCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(SOURCE_DELAY, THROUGHPUT, PROTOCOL), model.getFilteredFlashCardList());
    }

    /**
     * Parses {@code userInput} into a {@code QuestionContainsAnyKeywordsPredicate}.
     */
    private QuestionContainsAnyKeywordsPredicate preparePredicate(String userInput) {
        return new QuestionContainsAnyKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
