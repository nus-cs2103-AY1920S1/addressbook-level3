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
import seedu.flashcard.model.flashcard.Word;
import seedu.flashcard.model.tag.Tag;

/**
 * Make the flashcard item more json friendly.
 */
public class JsonAdaptedFlashcard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Flashcard's %s field is missing.";

    private final String word;
    private final List<JsonAdaptedChoice> choices = new ArrayList<>();
    private final String definition;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String answer;

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedFlashcard(@JsonProperty("word") String word,
                                @JsonProperty("choices") List<JsonAdaptedChoice> choices,
                                @JsonProperty("definition") String definition,
                                @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                                @JsonProperty("answer") String answer) {
        this.word = word;
        if (choices != null) {
            this.choices.addAll(choices);
        }
        this.definition = definition;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.answer = answer;
    }

    /**
     * Converts a given {@code Flashcard} into this class for Jackson use.
     */
    public JsonAdaptedFlashcard(Flashcard source) {
        word = source.getWord().word;
        choices.addAll(source.getChoices().stream().map(JsonAdaptedChoice::new).collect(Collectors.toList()));
        definition = source.getDefinition().definition;
        tagged.addAll(source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        answer = source.getAnswer().answer;
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

        final List<Choice> flashcardChoices = new ArrayList<>();
        for (JsonAdaptedChoice choice : choices) {
            flashcardChoices.add(choice.toModelType());
        }

        if (word == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Word.class.getSimpleName()));
        }
        if (!Word.isValidWord(word)) {
            throw new IllegalValueException(Word.MESSAGE_CONSTRAINTS);
        }
        final Word modelWord = new Word(word);

        if (definition == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Definition.class.getSimpleName()));
        }
        if (!Definition.isValidDefinition(definition)) {
            throw new IllegalValueException(Definition.MESSAGE_CONSTRAINTS);
        }
        final Definition modelDefinition = new Definition(definition);

        if (!Answer.isValidAnswer(answer)) {
            throw new IllegalValueException(Answer.MESSAGE_CONSTRAINTS);
        }
        final Answer modelAnswer = new Answer(answer);

        final Set<Choice> modelChoices = new HashSet<>(flashcardChoices);

        final Set<Tag> modelTags = new HashSet<>(flashcardTags);

        return new Flashcard(modelWord, modelChoices, modelDefinition, modelTags, modelAnswer);
    }
}
