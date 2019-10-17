package seedu.address.storage;

        import com.fasterxml.jackson.annotation.JsonCreator;
        import com.fasterxml.jackson.annotation.JsonProperty;

        import seedu.address.commons.exceptions.IllegalValueException;
        import seedu.address.model.question.Answer;
        import seedu.address.model.question.QuestionBody;
        import seedu.address.model.quiz.QuizResult;

/**
 * Jackson-friendly version of {@link QuizResult}.
 */
class JsonAdaptedQuizResult {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Quiz result's %s field is missing!";

    private final String answer;
    private final String questionBody;
    private final String quizTime;
    private final String result;

    /**
     * Constructs a {@code JsonAdaptedQuizResult} with the given quiz results.
     */
    @JsonCreator
    public JsonAdaptedQuizResult(@JsonProperty("answer") String answer,
                                 @JsonProperty("questionBody") String questionBody,
                                 @JsonProperty("quizTime") String quizTime, @JsonProperty("result") String result) {
        this.answer = answer;
        this.questionBody = questionBody;
        this.quizTime = quizTime;
        this.result = result;
    }

    /**
     * Converts a given {@code QuizResult} into this class for Jackson use.
     */
    public JsonAdaptedQuizResult(QuizResult source) {
        answer = source.getAnswer().toString();
        questionBody = source.getQuestionBody().toString();
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

        if (quizTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "quizTime"));
        }
        final String modelQuizTime = quizTime;

        if (result == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "result"));
        }
        final boolean modelResult = Boolean.parseBoolean(result);

        return new QuizResult(modelAnswer, modelQuestionBody, modelQuizTime, modelResult);
    }
}