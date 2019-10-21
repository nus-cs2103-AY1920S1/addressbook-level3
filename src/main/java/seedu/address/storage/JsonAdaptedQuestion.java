package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionBody;
import seedu.address.model.question.Subject;

/**
 * Jackson-friendly version of {@link Question}.
 */
public class JsonAdaptedQuestion {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Question's %s field is missing!";

    private final String questionBody;
    private final String answer;
    private final String subject;
    private final String difficulty;

    /**
     * Constructs a {@code JsonAdaptedQuestion} with the given question details.
     */
    @JsonCreator
    public JsonAdaptedQuestion(@JsonProperty("questionBody") String questionBody,
                               @JsonProperty("answer") String answer, @JsonProperty("subject") String subject,
                               @JsonProperty("difficulty") String difficulty) {
        this.questionBody = questionBody;
        this.answer = answer;
        this.subject = subject;
        this.difficulty = difficulty;
    }

    /**
     * Converts a given {@code Question} into this class for Jackson use.
     */
    public JsonAdaptedQuestion(Question source) {
        questionBody = source.getQuestionBody().body;
        answer = source.getAnswer().answer;
        subject = source.getSubject().subject;
        difficulty = source.getDifficulty().difficulty;
    }

    /**
     * Converts this Jackson-friendly adapted question object into the model's {@code Question} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted question.
     */
    public Question toModelType() throws IllegalValueException {

        if (questionBody == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    QuestionBody.class.getSimpleName()));
        }
        if (!QuestionBody.isValidQuestionBody(questionBody)) {
            throw new IllegalValueException(QuestionBody.MESSAGE_CONSTRAINTS);
        }
        final QuestionBody modelQuestionBody = new QuestionBody(questionBody);

        if (answer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName()));
        }
        if (!Answer.isValidAnswer(answer)) {
            throw new IllegalValueException(Answer.MESSAGE_CONSTRAINTS);
        }
        final Answer modelAnswer = new Answer(answer);

        if (subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
        }
        if (!Subject.isValidSubject(subject)) {
            throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
        }
        final Subject modelSubject = new Subject(subject);

        if (difficulty == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Difficulty.class.getSimpleName()));
        }
        if (!Difficulty.isValidDifficulty(difficulty)) {
            throw new IllegalValueException(Difficulty.MESSAGE_CONSTRAINTS);
        }
        final Difficulty modelDifficulty = new Difficulty(difficulty);

        return new Question(modelQuestionBody, modelAnswer, modelSubject, modelDifficulty);
    }
}
