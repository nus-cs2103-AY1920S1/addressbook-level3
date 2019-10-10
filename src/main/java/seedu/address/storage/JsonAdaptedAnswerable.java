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
import seedu.address.model.answerable.Answer;
import seedu.address.model.answerable.Category;
import seedu.address.model.answerable.Difficulty;
import seedu.address.model.answerable.Mcq;
import seedu.address.model.answerable.McqAnswer;
import seedu.address.model.answerable.Question;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Answerable}.
 */
class JsonAdaptedAnswerable {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Answerable's %s field is missing!";

    private final String question;
    private final String answer;
    private final String difficulty;
    private final String category;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAnswerable} with the given answerable details.
     */
    @JsonCreator
    public JsonAdaptedAnswerable(@JsonProperty("question") String question, @JsonProperty("answer") String answer,
                 @JsonProperty("difficulty") String difficulty, @JsonProperty("category") String category,
                 @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.question = question;
        this.answer = answer;
        this.difficulty = difficulty;
        this.category = category;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Answerable} into this class for Jackson use.
     */
    public JsonAdaptedAnswerable(Answerable source) {
        question = source.getQuestion().fullQuestion;
        answer = source.getAnswer().value;
        difficulty = source.getDifficulty().value;
        category = source.getCategory().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted answerable object into the model's {@code Answerable} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted answerable.
     */
    public Answerable toModelType() throws IllegalValueException {
        final List<Tag> answerableTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            answerableTags.add(tag.toModelType());
        }

        if (question == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_CONSTRAINTS);
        }
        final Question modelQuestion = new Question(question);

        if (difficulty == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Difficulty.class.getSimpleName()));
        }
        if (!Difficulty.isValidDifficulty(difficulty)) {
            throw new IllegalValueException(Difficulty.MESSAGE_CONSTRAINTS);
        }
        final Difficulty modelDifficulty = new Difficulty(difficulty);
        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName()));
        }
        if (!Category.isValidCategory(category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        final Category modelCategory = new Category(category);

        final Set<Tag> modelTags = new HashSet<>(answerableTags);

        //TODO: isValidAnswer always returns true
        if (answer == null) {
            throw new IllegalValueException(McqAnswer.MESSAGE_CONSTRAINTS);
        }
        final McqAnswer modelAnswer = new McqAnswer(answer);

        return new Mcq(modelQuestion, modelAnswer, modelDifficulty, modelCategory, modelTags);
    }

}
