package seedu.flashcard.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.flashcard.commons.exceptions.IllegalValueException;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.flashcard.Score;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.model.tag.Tag;

/**
 * Make the flashcard item more json friendly.
 */
public class JsonAdaptedFlashcard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Flashcard's %s field is missing.";

    private final String question;
    private final String definition;
    private final String answer;
    private final String type;
    private final String score;
    private final List<JsonAdaptedChoice> choices = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedFlashcard(@JsonProperty("question") String question,
                                @JsonProperty("choices") List<JsonAdaptedChoice> choices,
                                @JsonProperty("definition") String definition,
                                @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                                @JsonProperty("answer") String answer,
                                @JsonProperty("score") String score,
                                @JsonProperty("type") String type) {
        this.question = question;
        if (choices != null) {
            this.choices.addAll(choices);
        }
        this.definition = definition;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.answer = answer;
        this.type = type;
        this.score = score;
    }

    /**
     * Converts a given {@code Flashcard} into this class for Jackson use.
     */
    public JsonAdaptedFlashcard(Flashcard source) {
        question = source.getQuestion().question;
        if (source.isMcq()) {
            McqFlashcard mcqCard = (McqFlashcard) source;
            choices.addAll(mcqCard.getChoices().stream().map(JsonAdaptedChoice::new).collect(Collectors.toList()));
        }
        definition = source.getDefinition().definition;
        tagged.addAll(source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        answer = source.getAnswer().answer;
        if (source.isMcq()) {
            type = "McqFlashcard";
        } else {
            type = "ShortAnswerFlashcard";
        }
        score = new JsonAdaptedScore(source.getScore()).getScore();
    }

    /**
     * Converts this Jackson-friendly adapted flashcard object into the model's {@code Flashcard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashcard.
     */
    public Flashcard toModelType() throws IllegalValueException {
        final List<Tag> flashcardTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            flashcardTags.add(tag.toModelType());
        }

        if (question == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_CONSTRAINTS);
        }
        final Question modelQuestion = new Question(question);

        if (definition == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Definition.class.getSimpleName()));
        }
        if (!Definition.isValidDefinition(definition)) {
            throw new IllegalValueException(Definition.MESSAGE_CONSTRAINTS);
        }
        final Definition modelDefinition = new Definition(definition);

        if (answer == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName()));
        }
        if (!Answer.isValidAnswer(answer)) {
            throw new IllegalValueException(Answer.MESSAGE_CONSTRAINTS);
        }
        final Answer modelAnswer = new Answer(answer);

        final Set<Tag> modelTags = new HashSet<>(flashcardTags);

        final List<Choice> flashcardChoices = new ArrayList<>();

        if (score == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Score.class.getSimpleName()));
        }
        if (!Score.isValidScore(score)) {
            throw new IllegalValueException(Definition.MESSAGE_CONSTRAINTS);
        }
        String[] splitScore = score.split(" ");
        int correctAnswer = Integer.parseInt(splitScore[0]);
        int wrongAnswer = Integer.parseInt(splitScore[1]);
        final Score modelScore = new Score(correctAnswer, wrongAnswer);

        for (JsonAdaptedChoice choice : choices) {
            flashcardChoices.add(choice.toModelType());
        }

        final List<Choice> modelChoices = new ArrayList<>(flashcardChoices);

        if (type.equals("McqFlashcard")) {
            McqFlashcard flashcard =
                new McqFlashcard(modelQuestion, modelChoices, modelDefinition, modelTags, modelAnswer, modelScore);
            return flashcard;
        } else if (type.equals("ShortAnswerFlashcard")) {
            ShortAnswerFlashcard flashcard =
                new ShortAnswerFlashcard(modelQuestion, modelDefinition, modelTags, modelAnswer, modelScore);
            return flashcard;
        } else {
            throw new IllegalValueException("Issue in saved file, the flashcard type is incorrect.");
        }
    }
}

