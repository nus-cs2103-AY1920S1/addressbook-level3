package seedu.address.storage.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.ReadOnlyQuizzes;
import seedu.address.model.quiz.SavedQuizzes;

/**
 * An Immutable savedQuestions that is serializable to JSON format.
 */
@JsonRootName(value = "savedQuizzes")
class JsonSerializableQuizzes {

    public static final String MESSAGE_DUPLICATE_QUESTION = "Quizzes list contains duplicate quizzes.";

    private final List<JsonAdaptedQuiz> quizzes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableQuizzes} with the given questions.
     */
    @JsonCreator
    public JsonSerializableQuizzes(
        @JsonProperty("quizzes") List<JsonAdaptedQuiz> quizzes) {
        this.quizzes.addAll(quizzes);
    }

    /**
     * Converts a given {@code ReadOnlyQuizzes} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code
     *               JsonSerializableQuizzes}.
     */
    public JsonSerializableQuizzes(ReadOnlyQuizzes source) {
        quizzes.addAll(source.getSavedQuizzes().stream().map(JsonAdaptedQuiz::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts the saved quizzes into the model's {@code savedQuizzes} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SavedQuizzes toModelType() throws IllegalValueException {
        SavedQuizzes savedQuizzes = new SavedQuizzes();
        for (JsonAdaptedQuiz jsonAdaptedQuiz : quizzes) {
            Quiz quiz = jsonAdaptedQuiz.toModelType();
            savedQuizzes.addQuiz(quiz);
        }
        return savedQuizzes;
    }

}
