package seedu.address.testutil.quiz;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.model.question.Question;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.SavedQuizzes;
import seedu.address.testutil.question.TypicalQuestions;

/**
 * A utility class containing a list of {@code Quiz} objects to be used in tests.
 */
public class TypicalSavedQuizzes {

    public static final ArrayList<Question> QUESTION_LIST_ONE =
            new ArrayList<>(Arrays.asList(TypicalQuestions.QUESTION_ONE, TypicalQuestions.QUESTION_TWO));
    public static final ArrayList<Question> QUESTION_LIST_TWO =
            new ArrayList<>(Arrays.asList(TypicalQuestions.QUESTION_THREE, TypicalQuestions.QUESTION_FOUR));
    public static final ArrayList<Question> QUESTION_LIST_THREE =
            new ArrayList<>(Arrays.asList(TypicalQuestions.QUESTION_ONE, TypicalQuestions.QUESTION_THREE));
    public static final ArrayList<Question> QUESTION_LIST_FOUR =
            new ArrayList<>(Arrays.asList(TypicalQuestions.QUESTION_TWO, TypicalQuestions.QUESTION_FOUR));

    public static final Quiz QUIZ_ONE = new QuizBuilder().withQuizId("TestQUIZ_ONE")
            .withQuestionList(QUESTION_LIST_ONE).build();
    public static final Quiz QUIZ_TWO = new QuizBuilder().withQuizId("TestQUIZ_TWO")
            .withQuestionList(QUESTION_LIST_TWO).build();
    public static final Quiz QUIZ_THREE = new QuizBuilder().withQuizId("TestQUIZ_THREE")
            .withQuestionList(QUESTION_LIST_THREE).build();
    public static final Quiz QUIZ_FOUR = new QuizBuilder().withQuizId("TestQUIZ_FOUR")
            .withQuestionList(QUESTION_LIST_FOUR).build();

    public static SavedQuizzes getSavedQuizzes() {
        SavedQuizzes savedQuizzes = new SavedQuizzes();
        savedQuizzes.setQuizzes(getTypicalSavedQuizzes());
        return savedQuizzes;
    }

    public static ArrayList<Quiz> getTypicalSavedQuizzes() {
        return new ArrayList<>(Arrays.asList(QUIZ_ONE, QUIZ_TWO, QUIZ_THREE, QUIZ_FOUR));
    }
}
