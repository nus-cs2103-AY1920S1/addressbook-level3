package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.answerable.Answerable;

import seedu.address.model.answerable.Difficulty;
import seedu.address.model.answerable.Mcq;
import seedu.address.model.answerable.AnswerSet;
import seedu.address.model.answerable.Question;
import seedu.address.model.category.Category;

/**
 * Jackson-friendly version of {@link Answerable}.
 */
class JsonAdaptedAnswerable {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Answerable's %s field is missing!";

    private final String question;
    private final AnswerSet answerSet;
    private final String difficulty;
    private final List<JsonAdaptedCategory> categories = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAnswerable} with the given answerable details.
     */
    @JsonCreator
    public JsonAdaptedAnswerable(@JsonProperty("question") String question, @JsonProperty("answerSet") AnswerSet answerSet,
                 @JsonProperty("difficulty") String difficulty,
                 @JsonProperty("tagged") List<JsonAdaptedCategory> categories) {
        this.question = question;
        this.answerSet = answerSet;
        this.difficulty = difficulty;
        if (categories != null) {
            this.categories.addAll(categories);
        }
    }

    /**
     * Converts a given {@code Answerable} into this class for Jackson use.
     */
    public JsonAdaptedAnswerable(Answerable source) {
        question = source.getQuestion().fullQuestion;
        difficulty = source.getDifficulty().value;
        answerSet = source.getAnswerSet();
        categories.addAll(source.getCategories().stream()
                .map(JsonAdaptedCategory::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted answerable object into the model's {@code Answerable} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted answerable.
     */
    public Answerable toModelType() throws IllegalValueException {
        final List<Category> answerableTags = new ArrayList<>();
        for (JsonAdaptedCategory category : categories) {
            answerableTags.add(category.toModelType());
        }

        if (question == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_CONSTRAINTS);
        }
        final Question modelQuestion = new Question(question);

        //TODO: isValidAnswer always returns true
        if (answerSet == null) {
            throw new IllegalValueException(AnswerSet.MESSAGE_CONSTRAINTS);
        }
        final AnswerSet modelAnswer = answerSet;

        if (difficulty == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Difficulty.class.getSimpleName()));
        }
        if (!Difficulty.isValidDifficulty(difficulty)) {
            throw new IllegalValueException(Difficulty.MESSAGE_CONSTRAINTS);
        }
        final Difficulty modelDifficulty = new Difficulty(difficulty);

        final Set<Category> modelCategories = new HashSet<>(answerableTags);

        //TODO: Implement Answerable
        return new Mcq(modelQuestion, modelAnswer, modelDifficulty, modelCategories);
    }
}
