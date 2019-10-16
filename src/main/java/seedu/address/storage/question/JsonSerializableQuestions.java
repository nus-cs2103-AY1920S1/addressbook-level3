package seedu.address.storage.question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.question.Question;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.question.SavedQuestions;

/**
 * An Immutable savedQuestions that is serializable to JSON format.
 */
@JsonRootName(value = "savedQuestions")
class JsonSerializableQuestions {

    public static final String MESSAGE_DUPLICATE_QUESTION = "Questions list contains duplicate question(s).";

    private final List<JsonAdaptedQuestion> questions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableQuestions} with the given questions.
     */
    @JsonCreator
    public JsonSerializableQuestions(
        @JsonProperty("questions") List<JsonAdaptedQuestion> questions) {
        this.questions.addAll(questions);
    }

    /**
     * Converts a given {@code ReadOnlyQuestions} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code
     *               JsonSerializableQuestions}.
     */
    public JsonSerializableQuestions(ReadOnlyQuestions source) {
        questions.addAll(source.getSavedQuestions().stream().map(JsonAdaptedQuestion::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts the saved questions into the model's {@code savedQuestions} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SavedQuestions toModelType() throws IllegalValueException {
        SavedQuestions savedQuestions = new SavedQuestions();
        for (JsonAdaptedQuestion jsonAdaptedQuestion : questions) {
            Question question = jsonAdaptedQuestion.toModelType();
            if (savedQuestions.hasQuestion(question)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_QUESTION);
            }
            savedQuestions.addQuestion(question);
        }
        return savedQuestions;
    }

}
