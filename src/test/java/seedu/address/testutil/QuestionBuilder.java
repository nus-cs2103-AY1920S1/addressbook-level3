package seedu.address.testutil;

import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionBody;
import seedu.address.model.question.Subject;

/**
 * A utility class to help with building Question objects.
 */
public class QuestionBuilder {
    public static final String DEFAULT_QUESTION_BODY = "A question";
    public static final String DEFAULT_ANSWER = "An answer";
    public static final String DEFAULT_SUBJECT = "CS2103T";
    public static final String DEFAULT_DIFFICULTY = "easy";

    private QuestionBody questionBody;
    private Answer answer;
    private Subject subject;
    private Difficulty difficulty;

    public QuestionBuilder() {
        questionBody = new QuestionBody(DEFAULT_QUESTION_BODY);
        answer = new Answer(DEFAULT_ANSWER);
        subject = new Subject(DEFAULT_SUBJECT);
        difficulty = new Difficulty(DEFAULT_DIFFICULTY);
    }

    /**
     * Initializes the QuestionBuilder with the data of {@code questionToCopy}.
     */
    public QuestionBuilder(Question questionToCopy) {
        questionBody = questionToCopy.getQuestionBody();
        answer = questionToCopy.getAnswer();
    }

    /**
     * Sets the {@code QuestionBody} of the {@code Question} that we are building.
     */
    public QuestionBuilder withQuestionBody(String questionBody) {
        this.questionBody = new QuestionBody(questionBody);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Question} that we are building.
     */
    public QuestionBuilder withAnswer(String answer) {
        this.answer = new Answer(answer);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Question} that we are building.
     */
    public QuestionBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code Question} that we are building.
     */
    public QuestionBuilder withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    public Question build() {
        return new Question(questionBody, answer, subject, difficulty);
    }

}
