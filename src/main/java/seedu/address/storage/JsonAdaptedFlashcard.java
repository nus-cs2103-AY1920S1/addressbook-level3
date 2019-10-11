package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.Category;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;


/**
 * Jackson-friendly version of {@link FlashCard}.
 */
class JsonAdaptedFlashcard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "FlashCard's %s field is missing!";

    private final String question;
    private final String answer;
    private final String rating;
    private final List<JsonAdaptedCategory> categories = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashCard details.
     */
    @JsonCreator
    public JsonAdaptedFlashcard(@JsonProperty("question") String question,
                                @JsonProperty("answer") String answer,
                                @JsonProperty("rating") String rating,
                                @JsonProperty("categories") List<JsonAdaptedCategory> categories) {
        this.question = question;
        this.answer = answer;
        this.rating = rating;
        if (categories != null) {
            this.categories.addAll(categories);
        }
    }

    /**
     * Converts a given {@code FlashCard} into this class for Jackson use.
     */
    public JsonAdaptedFlashcard(FlashCard source) {
        question = source.getQuestion().fullQuestion;
        answer = source.getAnswer().fullAnswer;
        rating = source.getRating().value;
        categories.addAll(source.getCategories().stream()
                .map(JsonAdaptedCategory::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted flashCard object into the model's {@code FlashCard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashCard.
     */
    public FlashCard toModelType() throws IllegalValueException {
        final List<Category> flashCardCategories = new ArrayList<>();
        for (JsonAdaptedCategory category : categories) {
            flashCardCategories.add(category.toModelType());
        }

        if (question == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                            Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_CONSTRAINTS);
        }
        final Question modelQuestion = new Question(question);

        if (answer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName()));
        }
        if (!Answer.isValidAnswer(answer)) {
            throw new IllegalValueException(Answer.MESSAGE_CONSTRAINTS);
        }
        final Answer modelAnswer = new Answer(answer);

        if (rating == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName()));
        }
        if (!Rating.isValidRating(rating)) {
            throw new IllegalValueException(Rating.MESSAGE_CONSTRAINTS);
        }
        final Rating modelRating = new Rating(rating);

        final Set<Category> modelCategories = new HashSet<>(flashCardCategories);
        return new FlashCard(modelQuestion, modelAnswer, modelRating, modelCategories);
    }

}
