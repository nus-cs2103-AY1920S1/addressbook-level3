package seedu.address.testutil.quiz;

import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.QuestionList;
import seedu.address.model.quiz.Quiz;

/**
 * A utility class to help with building Quiz objects.
 * Example usage: <br>
 *     {@code QuizBuilder qb = new QuizBuilder().withQuiz("What is 1+1?").build();}
 */
public class QuizBuilder {

    public static final Question DEFAULT_OPEN_ENDED_QUESTION = new OpenEndedQuestion("How awesome is Njoy?",
            "Awesome!");
    public static final Question DEFAULT_MCQ_QUESTION = new McqQuestion("How awesome is Njoy?",
            "Awesome!", "Awesome", "Very awesome", "Ok", "No comment");
    public static final String DEFAULT_QUIZ_ID = "CS2103T";
    public static final QuestionList DEFAULT_QUESTION_LIST = new QuestionList();
    public static final int DEFAULT_QUESTION_INDEX = 1;
    public static final int DEFAULT_QUIZ_QUESTION_INDEX = 1;

    private String quizId;
    private QuestionList questionList;

    public QuizBuilder() {
        quizId = DEFAULT_QUIZ_ID;
        questionList = DEFAULT_QUESTION_LIST;
        questionList.addQuestion(DEFAULT_MCQ_QUESTION);
        questionList.addQuestion(DEFAULT_OPEN_ENDED_QUESTION);
    }

    /**
     * Sets the {@code Quiz} of the {@code Quiz} that we are building.
     */
    public QuizBuilder withQuizId(String quizId) {
        this.quizId = quizId;
        return this;
    }

    /**
     * Sets the {@code QuestionList} of the {@code Quiz} that we are building.
     */
    public QuizBuilder withQuestionList(QuestionList questionList) {
        this.questionList = questionList;
        return this;
    }

    /**
     * Builds the quiz with the specified fields.
     * @return Quiz object.
     */
    public Quiz build() {
        Quiz quiz = new Quiz(quizId);
        quiz.setQuestionList(questionList);
        return quiz;
    }
}
