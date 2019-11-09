package seedu.address.testutil.quiz;

import java.util.ArrayList;

import seedu.address.model.question.Question;
import seedu.address.model.quiz.QuestionList;
import seedu.address.model.quiz.Quiz;

/**
 * A utility class to help with building Quiz objects.
 * Example usage: <br>
 *     {@code QuizBuilder qb = new QuizBuilder().withQuiz("What is 1+1?").build();}
 */
public class QuizBuilder {

    public static final String DEFAULT_QUIZ_ID = "Quiz01";
    public static final QuestionList DEFAULT_QUESTION_LIST = new QuestionList();

    private QuestionList questionList;
    private String quizId;

    public QuizBuilder() {
        this.questionList = DEFAULT_QUESTION_LIST;
        this.quizId = DEFAULT_QUIZ_ID;
    }

    public QuizBuilder(Quiz quizToCopy) {
        questionList = quizToCopy.getQuestionList();
        quizId = quizToCopy.getQuizId();
    }

    /**
     * Sets the {@code quizId} of the {@code Quiz} that we are building.
     */
    public QuizBuilder withQuizId(String quizId) {
        this.quizId = quizId;
        return this;
    }

    /**
     * Sets the {@code QuestionList} of the {@code Quiz} that we are building.
     */
    public QuizBuilder withQuestionList(ArrayList<Question> questionsAsList) {
        QuestionList questionList = new QuestionList();
        questionList.setQuestions(questionsAsList);
        this.questionList = questionList;
        return this;
    }

    /**
     * Builds a quiz.
     * @return The built quiz.
     */
    public Quiz build() {
        Quiz quiz = new Quiz(quizId);
        quiz.setQuestionList(questionList);
        return quiz;
    }
}
