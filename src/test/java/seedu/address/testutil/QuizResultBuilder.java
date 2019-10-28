package seedu.address.testutil;

import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.QuestionBody;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizResult;

/**
 * A utility class to help with building Quiz Result objects.
 */
public class QuizResultBuilder {
    public static final String DEFAULT_ANSWER = "An answer";
    public static final String DEFAULT_QUESTION_BODY = "A question";
    public static final String DEFAULT_SUBJECT = "CS2103T";
    public static final String DEFAULT_DIFFICULTY = "Easy";
    public static final String DEFAULT_TIME = "2019/10/10 12:00";
    public static final String DEFAULT_RESULT = "true";

    private Answer answer;
    private QuestionBody questionBody;
    private Subject subject;
    private Difficulty difficulty;
    private String quizTime;
    private boolean result;

    public QuizResultBuilder() {
        answer = new Answer(DEFAULT_ANSWER);
        questionBody = new QuestionBody(DEFAULT_QUESTION_BODY);
        subject = new Subject(DEFAULT_SUBJECT);
        difficulty = new Difficulty(DEFAULT_DIFFICULTY);
        quizTime = DEFAULT_TIME;
        result = Boolean.parseBoolean(DEFAULT_RESULT);
    }

    /**
     * Initializes the QuizResultBuilder with the data of {@code quizResultToCopy}.
     */
    public QuizResultBuilder(QuizResult quizResultToCopy) {
        answer = quizResultToCopy.getAnswer();
        questionBody = quizResultToCopy.getQuestionBody();
        subject = quizResultToCopy.getSubject();
        difficulty = quizResultToCopy.getDifficulty();
        quizTime = quizResultToCopy.getQuizTime();
        result = quizResultToCopy.getResult();
    }

    /**
     * Sets the {@code Answer} of the {@code QuizResult} that we are building.
     */
    public QuizResultBuilder withAnswer(String answer) {
        this.answer = new Answer(answer);
        return this;
    }

    /**
     * Sets the {@code QuestionBody} of the {@code QuizResult} that we are building.
     */
    public QuizResultBuilder withQuestionBody(String questionBody) {
        this.questionBody = new QuestionBody(questionBody);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code QuizResult} that we are building.
     */
    public QuizResultBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code QuizResult} that we are building.
     */
    public QuizResultBuilder withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    /**
     * Sets the {@code QuizTime} of the {@code QuizResult} that we are building.
     */
    public QuizResultBuilder withQuizTime(String quizTime) {
        this.quizTime = quizTime;
        return this;
    }

    /**
     * Sets the {@code Result} of the {@code QuizResult} that we are building.
     */
    public QuizResultBuilder withResult(String result) {
        this.result = Boolean.parseBoolean(result);
        return this;
    }

    public QuizResult build() {
        return new QuizResult(answer, questionBody, subject, difficulty, quizTime, result);
    }
}
