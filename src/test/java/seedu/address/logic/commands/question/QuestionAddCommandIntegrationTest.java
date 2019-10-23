package seedu.address.logic.commands.question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventRecord;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.SavedQuizzes;
import seedu.address.model.statistics.StatisticsRecord;
import seedu.address.model.student.StudentRecord;

public class QuestionAddCommandIntegrationTest {

    private Model model = new ModelManager(new AddressBook(), new StudentRecord(),
        getTypicalSavedQuestions(), new SavedQuizzes(), new NotesRecord(), new EventRecord(), new StatisticsRecord(),
        new UserPrefs());

    @Test
    public void execute_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new QuestionAddCommand(null, null, null));
    }

    @Test
    public void execute_addOpenEndedQuestion_success() {
        String question = "What is 1+1?";
        String answer = "2";
        String type = "open";
        QuestionAddCommand addOpenCommand = new QuestionAddCommand(question, answer, type);

        Question expectedQuestion = new OpenEndedQuestion(question, answer);
        String expectedMessage = "Added question: " + expectedQuestion;
        assertCommandSuccess(addOpenCommand, model, expectedMessage, model);
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
        String expectedMessage = "Added question: " + expectedQuestion;
        assertCommandSuccess(addMcqCommand, model, expectedMessage, model);
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
