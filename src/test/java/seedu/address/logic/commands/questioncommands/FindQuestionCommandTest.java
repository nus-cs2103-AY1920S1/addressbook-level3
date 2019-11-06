package seedu.address.logic.commands.questioncommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import seedu.address.model.question.QuestionContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindQuestionCommand}.
 */
class FindQuestionCommandTest {
    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    void equals() {
        QuestionContainsKeywordsPredicate firstPredicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("first"));
        QuestionContainsKeywordsPredicate secondPredicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("second"));

        FindQuestionCommand findFirstCommand = new FindQuestionCommand(firstPredicate);
        FindQuestionCommand findSecondCommand = new FindQuestionCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindQuestionCommand findFirstCommandCopy = new FindQuestionCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different question -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    void execute_zeroKeywords_noQuestionFound() {
        String expectedMessage = String.format(MESSAGE_QUESTIONS_LISTED_OVERVIEW, 0);
        QuestionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindQuestionCommand command = new FindQuestionCommand(predicate);
        expectedModel.updateFilteredQuestionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredQuestionList());
    }

    @Test
    void execute_multipleKeywords_multipleQuestionsFound() {
        String expectedMessage = String.format(MESSAGE_QUESTIONS_LISTED_OVERVIEW, 3);
        QuestionContainsKeywordsPredicate predicate = preparePredicate("MVC TCP UDP");
        FindQuestionCommand command = new FindQuestionCommand(predicate);
        expectedModel.updateFilteredQuestionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MVC_QUESTION, TCP_QUESTION, UDP_QUESTION), model.getFilteredQuestionList());
    }

    /**
     * Parses {@code userInput} into a {@code BodyContainsKeywordsPredicate}.
     */
    private QuestionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new QuestionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
