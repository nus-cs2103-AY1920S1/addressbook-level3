package seedu.address.storage.quiz;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.quiz.Quiz;

/**
 * Jackson-friendly version of {@link Quiz}.
 */
class JsonAdaptedQuiz {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "quiz's %s field is missing!";

    private final String quizId;
    private final String questions;

    /**
     * Constructs a {@code JsonAdaptedQuiz} with the given quiz details.
     */
    @JsonCreator
    public JsonAdaptedQuiz(@JsonProperty("quizId") String quizId,
                           @JsonProperty("questions") String questions) {
        this.quizId = quizId;
        this.questions = questions;
    }

    /**
     * Converts a given {@code Quiz} into this class for Jackson use.
     */
    public JsonAdaptedQuiz(Quiz source) {
        quizId = source.getQuizId();
        questions = source.getStringQuestions();
    }

    /**
     * Converts this Jackson-friendly adapted quiz object into the model's {@code Quiz} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted quiz.
     */
    public Quiz toModelType() throws IllegalValueException {
        if (StringUtils.isBlank(quizId)) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "QUIZ"));
        }
        if (StringUtils.isBlank(questions)) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "QUIZ"));
        }

        if (quizId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "QUIZ_ID"));
        }

        Quiz quiz = new Quiz(quizId);
        quiz.setStringQuestions(questions);

        return quiz;
    }

}
