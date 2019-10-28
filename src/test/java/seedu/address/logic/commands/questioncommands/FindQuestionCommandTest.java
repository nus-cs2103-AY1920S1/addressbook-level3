package seedu.address.logic.commands.questioncommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_QUESTIONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppData.MVC_QUESTION;
import static seedu.address.testutil.TypicalAppData.TCP_QUESTION;
import static seedu.address.testutil.TypicalAppData.UDP_QUESTION;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.question.BodyContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindQuestionCommand}.
 */
public class FindQuestionCommandTest {
    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    public void equals() {
        BodyContainsKeywordsPredicate firstPredicate =
                new BodyContainsKeywordsPredicate(Collections.singletonList("first"));
        BodyContainsKeywordsPredicate secondPredicate =
                new BodyContainsKeywordsPredicate(Collections.singletonList("second"));

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

        // different question -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noQuestionFound() {
        String expectedMessage = String.format(MESSAGE_QUESTIONS_LISTED_OVERVIEW, 0);
        BodyContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindQuestionCommand command = new FindQuestionCommand(predicate);
        expectedModel.updateFilteredQuestionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredQuestionList());
    }

    @Test
    public void execute_multipleKeywords_multipleQuestionsFound() {
        String expectedMessage = String.format(MESSAGE_QUESTIONS_LISTED_OVERVIEW, 3);
        BodyContainsKeywordsPredicate predicate = preparePredicate("MVC TCP UDP");
        FindQuestionCommand command = new FindQuestionCommand(predicate);
        expectedModel.updateFilteredQuestionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MVC_QUESTION, TCP_QUESTION, UDP_QUESTION), model.getFilteredQuestionList());
    }

    /**
     * Parses {@code userInput} into a {@code BodyContainsKeywordsPredicate}.
     */
    private BodyContainsKeywordsPredicate preparePredicate(String userInput) {
        return new BodyContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
