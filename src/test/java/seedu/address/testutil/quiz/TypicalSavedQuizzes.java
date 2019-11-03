package seedu.address.testutil.quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.SavedQuizzes;

/**
 * A utility class containing a list of {@code Quiz} objects to be used in tests.
 */
public class TypicalSavedQuizzes {

    public static final Quiz QUIZ1 = new QuizBuilder().withQuizId("CS2103T").build();
    public static final Quiz QUIZ2 = new QuizBuilder().withQuizId("Quiz2").build();
    public static final Quiz QUIZ3 = new QuizBuilder().withQuizId("Quiz3").build();
    public static final Quiz QUIZ4 = new QuizBuilder().withQuizId("Quiz4").build();

    private TypicalSavedQuizzes() {}

    /**
     * Returns an {@code SavedQuizzes} with all the typical saved quizzes.
     */
    public static SavedQuizzes getTypicalSavedQuizzes() {
        SavedQuizzes ab = new SavedQuizzes();
        for (Quiz quiz : getTypicalQuizzes()) {
            ab.addQuiz(quiz);
        }
        return ab;
    }

    public static List<Quiz> getTypicalQuizzes() {
        return new ArrayList<>(Arrays.asList(QUIZ1, QUIZ2,
                QUIZ3, QUIZ4));
    }
}
