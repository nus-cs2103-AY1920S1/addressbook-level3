package seedu.revision.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.revision.commons.exceptions.IllegalValueException;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;

import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.category.Category;

/**
 * Jackson-friendly version of {@link Answerable}.
 */
class JsonAdaptedAnswerable {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Answerable's %s field is missing!";

    private final String question;
    private final List<JsonAdaptedAnswer> correctAnswerSet = new ArrayList<>();
    private final List<JsonAdaptedAnswer> wrongAnswerSet = new ArrayList<>();
    private final String difficulty;
    private final List<JsonAdaptedCategory> categories = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAnswerable} with the given answerable details.
     */
    @JsonCreator
    public JsonAdaptedAnswerable(@JsonProperty("question") String question,
             @JsonProperty("correctAnswerSet") List<JsonAdaptedAnswer> correctAnswerSet,
             @JsonProperty("wrongAnswerSet") List<JsonAdaptedAnswer> wrongAnswerSet,
             @JsonProperty("difficulty") String difficulty,
             @JsonProperty("categories") List<JsonAdaptedCategory> categories) {
        this.question = question;
        if (correctAnswerSet != null) {
            this.correctAnswerSet.addAll(correctAnswerSet);
        }
        if (wrongAnswerSet != null) {
            this.wrongAnswerSet.addAll(wrongAnswerSet);
        }
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
        correctAnswerSet.addAll(source.getCorrectAnswerSet().stream()
                .map(JsonAdaptedAnswer::new)
                .collect(Collectors.toList()));
        wrongAnswerSet.addAll(source.getWrongAnswerSet().stream()
                .map(JsonAdaptedAnswer::new)
                .collect(Collectors.toList()));
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

        final List<Answer> correctAnswers = new ArrayList<>();
        for (JsonAdaptedAnswer correctAnswer : correctAnswerSet) {
            correctAnswers.add(correctAnswer.toModelType());
        }

        final List<Answer> wrongAnswers = new ArrayList<>();
        for (JsonAdaptedAnswer wrongAnswer : wrongAnswerSet) {
            wrongAnswers.add(wrongAnswer.toModelType());
        }

        if (question == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_CONSTRAINTS);
        }
        final Question modelQuestion = new Question(question);

        final Set<Answer> modelCorrectAnswerSet = new HashSet<>(correctAnswers);

        final Set<Answer> modelWrongAnswerSet = new HashSet<>(wrongAnswers);

        if (difficulty == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Difficulty.class.getSimpleName()));
        }
        if (!Difficulty.isValidDifficulty(difficulty)) {
            throw new IllegalValueException(Difficulty.MESSAGE_CONSTRAINTS);
        }
        final Difficulty modelDifficulty = new Difficulty(difficulty);

        final Set<Category> modelCategories = new HashSet<>(answerableTags);

        //TODO: Implement Answerable
        return new Mcq(modelQuestion, modelCorrectAnswerSet, modelWrongAnswerSet, modelDifficulty, modelCategories);
    }
}
