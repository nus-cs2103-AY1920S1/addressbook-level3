package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.QuestionBody;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizResult;

/**
 * Jackson-friendly version of {@link QuizResult}.
 */
class JsonAdaptedQuizResult {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Quiz result's %s field is missing!";

    private final String answer;
    private final String questionBody;
    private final String subject;
    private final String difficulty;
    private final String quizTime;
    private final String result;

    /**
     * Constructs a {@code JsonAdaptedQuizResult} with the given quiz results.
     */
    @JsonCreator
    public JsonAdaptedQuizResult(@JsonProperty("answer") String answer,
                                 @JsonProperty("questionBody") String questionBody,
                                 @JsonProperty("subject") String subject, @JsonProperty("difficulty") String difficulty,
                                 @JsonProperty("quizTime") String quizTime, @JsonProperty("result") String result) {
        this.answer = answer;
        this.questionBody = questionBody;
        this.subject = subject;
        this.difficulty = difficulty;
        this.quizTime = quizTime;
        this.result = result;
    }

    /**
     * Converts a given {@code QuizResult} into this class for Jackson use.
     */
    public JsonAdaptedQuizResult(QuizResult source) {
        answer = source.getAnswer().toString();
        questionBody = source.getQuestionBody().toString();
        subject = source.getSubject().toString();
        difficulty = source.getDifficulty().toString();
        quizTime = source.getQuizTime();
        result = String.valueOf(source.getResult());
    }

    /**
     * Converts this Jackson-friendly adapted quiz result object into the model's {@code QuizResult} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted quiz result.
     */
    public QuizResult toModelType() throws IllegalValueException {
        if (answer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName()));
        }
        final Answer modelAnswer = new Answer(answer);

        if (questionBody == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    QuestionBody.class.getSimpleName()));
        }
        final QuestionBody modelQuestionBody = new QuestionBody(questionBody);

        if (subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Subject.class.getSimpleName()));
        }
        final Subject modelSubject = new Subject(subject);

        if (difficulty == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Difficulty.class.getSimpleName()));
        }
        final Difficulty modelDifficulty = new Difficulty(difficulty);

        if (quizTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "quizTime"));
        }

        if (result == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "result"));
        }
        final boolean modelResult = Boolean.parseBoolean(result);

        return new QuizResult(modelAnswer, modelQuestionBody, modelSubject,
                modelDifficulty, quizTime, modelResult);
    }
}
