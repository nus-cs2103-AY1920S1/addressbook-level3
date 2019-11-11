package seedu.address.logic.commands.question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;

public class QuestionAddCommandIntegrationTest {

    private Model model = new ModelManager();

    public QuestionAddCommandIntegrationTest() {
        model.setSavedQuestions(getTypicalSavedQuestions());
    }

    @Test
    public void execute_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new QuestionAddCommand(null, null, null));
    }

    @Test
    public void execute_addOpenEndedQuestion_success() {
        String question = "What is 1+2?";
        String answer = "3";
        String type = "open";
        QuestionAddCommand addOpenCommand = new QuestionAddCommand(question, answer, type);

        Question expectedQuestion = new OpenEndedQuestion(question, answer);
        String expectedMessage = String
            .format(QuestionAddCommand.MESSAGE_SUCCESS, expectedQuestion);
        assertCommandSuccess(addOpenCommand, model,
            new CommandResult(expectedMessage, CommandResultType.SHOW_QUESTION), model);
    }

    @Test
    public void execute_addMcqQuestion_success() {
        String question = "What is 1+1?";
        String answer = "2";
        String type = "mcq";
        String optionA = "1";
        String optionB = "2";
        String optionC = "3";
        String optionD = "4";
        QuestionAddCommand addMcqCommand = new QuestionAddCommand(question, answer, type, optionA,
            optionB, optionC, optionD);

        Question expectedQuestion = new McqQuestion(question, answer, optionA, optionB, optionC,
            optionD);
        String expectedMessage = String
            .format(QuestionAddCommand.MESSAGE_SUCCESS, expectedQuestion);
        assertCommandSuccess(addMcqCommand, model,
            new CommandResult(expectedMessage, CommandResultType.SHOW_QUESTION), model);
    }

    @Test
    public void equals() {
        QuestionAddCommand addOpenCommand = new QuestionAddCommand("What is 1+1?", "2", "open");
        QuestionAddCommand addMcqCommand = new QuestionAddCommand("What is 1+1?", "2", "mcq", "1",
            "2", "3", "4");

        // Same object
        assertTrue(addOpenCommand.equals(addOpenCommand));
        assertTrue(addMcqCommand.equals(addMcqCommand));

        // Null
        assertFalse(addOpenCommand.equals(null));
        assertFalse(addMcqCommand.equals(null));

        // Different questions
        assertFalse(addOpenCommand.equals(addMcqCommand));
    }

}
