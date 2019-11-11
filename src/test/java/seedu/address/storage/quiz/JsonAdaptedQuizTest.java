package seedu.address.storage.quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.quiz.JsonAdaptedQuiz.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.Quiz;
import seedu.address.testutil.question.QuestionBuilder;
import seedu.address.testutil.quiz.QuizBuilder;

public class JsonAdaptedQuizTest {

    private static final String QUIZ_ID = "1";
    private static final String QUESTIONS_STRING = "1";
    private static final String INVALID_QUIZ_ID = "";
    private static final String INVALID_QUESTIONS_STRING = "";
    private static final Question QUESTION = new QuestionBuilder().withQuestion("What is 1+1?")
        .withAnswer("2").build();
    private static final ArrayList<Question> QUESTIONS = new ArrayList<Question>(
        Arrays.asList(QUESTION));

    private static final Quiz quiz = new QuizBuilder().withQuizId(QUIZ_ID)
        .withQuestionList(QUESTIONS).build();

    @Test
    public void toModelType_validQuizDetails_returnsQuiz() throws Exception {
        JsonAdaptedQuiz quiz = new JsonAdaptedQuiz(QUIZ_ID, QUESTIONS_STRING);
        assertEquals(this.quiz, quiz.toModelType());
    }

    @Test
    public void toModelType_invalidQuiz_throwsIllegalValueException() {
        JsonAdaptedQuiz quiz = new JsonAdaptedQuiz(INVALID_QUIZ_ID, QUESTIONS_STRING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "QUIZ");
        assertThrows(IllegalValueException.class, expectedMessage, quiz::toModelType);
    }

    @Test
    public void toModelType_invalidQuestions_throwsIllegalValueException() {
        JsonAdaptedQuiz quiz = new JsonAdaptedQuiz(QUIZ_ID, INVALID_QUESTIONS_STRING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "QUIZ");
        assertThrows(IllegalValueException.class, expectedMessage, quiz::toModelType);
    }

    @Test
    public void toModelType_nullQuiz_throwsIllegalValueException() {
        JsonAdaptedQuiz quiz = new JsonAdaptedQuiz(null, QUESTIONS_STRING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "QUIZ");
        assertThrows(IllegalValueException.class, expectedMessage, quiz::toModelType);
    }
}
