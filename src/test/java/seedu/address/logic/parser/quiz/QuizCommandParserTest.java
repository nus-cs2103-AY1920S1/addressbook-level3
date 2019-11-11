package seedu.address.logic.parser.quiz;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.quiz.QuizAddQuestionCommand;
import seedu.address.logic.commands.quiz.QuizCreateAutomaticallyCommand;
import seedu.address.logic.commands.quiz.QuizCreateManuallyCommand;
import seedu.address.logic.commands.quiz.QuizDeleteQuestionCommand;
import seedu.address.logic.commands.quiz.QuizExportCommand;
import seedu.address.logic.commands.quiz.QuizListQuestionsAndAnswersCommand;
import seedu.address.logic.commands.quiz.QuizShowAnswersCommand;
import seedu.address.logic.commands.quiz.QuizShowQuestionsCommand;

/**
 * Test for QuizCommandParser.
 */
public class QuizCommandParserTest {
    private final QuizCommandParser parser = new QuizCommandParser();

    /**
     * Tests for successfully creating a QuizCreateManuallyCommand.
     */
    @Test
    public void parseCommand_createQuizManualValidCommand_success() throws Exception {
        Command command = parser.parse(" manual/ quizID/G01 questionNumber/1 2 3");
        assertTrue(command instanceof QuizCreateManuallyCommand);
    }

    /**
     * Tests for successfully creating a QuizCreateAutomaticallyCommand.
     */
    @Test
    public void parseCommand_createQuizAutoValidCommand_success() throws Exception {
        Command command = parser.parse(" auto/ quizID/G01 numQuestions/2 type/all");
        assertTrue(command instanceof QuizCreateAutomaticallyCommand);
    }

    /**
     * Tests for successfully creating a QuizAddQuestionCommand.
     */
    @Test
    public void parseCommand_addQuestionToQuizValidCommand_success() throws Exception {
        Command command = parser.parse(" add quizID/G03 questionNumber/1 quizQuestionNumber/1");
        assertTrue(command instanceof QuizAddQuestionCommand);
    }

    /**
     * Tests for successfully creating a QuizDeleteQuestionCommand.
     */
    @Test
    public void parseCommand_deleteQuestionFromQuizValidCommand_success() throws Exception {
        Command command = parser.parse(" delete quizID/G03 quizQuestionNumber/1");
        assertTrue(command instanceof QuizDeleteQuestionCommand);
    }

    /**
     * Tests for successfully creating a QuizListQuestionsAndAnswersCommand.
     */
    @Test
    public void parseCommand_listQuestionsAndAnswersValidCommand_success() throws Exception {
        Command command = parser.parse(" quizID/G01");
        assertTrue(command instanceof QuizListQuestionsAndAnswersCommand);
    }

    /**
     * Tests for successfully creating a QuizShowQuestionsCommand.
     */
    @Test
    public void parseCommand_showQuestionsValidCommand_success() throws Exception {
        Command command = parser.parse(" showQuestions quizID/G01");
        assertTrue(command instanceof QuizShowQuestionsCommand);
    }

    /**
     * Tests for successfully creating a QuizShowAnswersCommand.
     */
    @Test
    public void parseCommand_showAnswersValidCommand_success() throws Exception {
        Command command = parser.parse(" showAnswers quizID/G01");
        assertTrue(command instanceof QuizShowAnswersCommand);
    }

    /**
     * Tests for successfully creating a QuizExportCommand.
     */
    @Test
    public void parseCommand_exportQuizValidCommand_success() throws Exception {
        Command command = parser.parse(" export quizID/G01");
        assertTrue(command instanceof QuizExportCommand);
    }

}
