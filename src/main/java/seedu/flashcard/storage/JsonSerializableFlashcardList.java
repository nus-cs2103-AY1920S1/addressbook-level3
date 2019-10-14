package seedu.flashcard.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.flashcard.commons.exceptions.IllegalValueException;
import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.ReadOnlyFlashcardList;
import seedu.flashcard.model.flashcard.Flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable FlashcardList that is serializable to JSON format.
 */
@JsonRootName(value = "flashcardlist")
public class JsonSerializableFlashcardList {

    public static final String MESSAGE_DUPLICATE_FLASHCARD = "Flashcards list contains duplicate persons";

    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFlashcardList} with the given flashcards.
     */
    @JsonCreator
    public JsonSerializableFlashcardList(@JsonProperty("flashcards") List<JsonAdaptedFlashcard> flashcards) {
        this.flashcards.addAll(flashcards);
    }

    /**
     * Converts a given {@code ReadOnlyFlashcardList} into this class for Jackson use.
     * @param source future changes to this will not affect the created {@code JsonSerializableFlashcardList}.
     */
    public JsonSerializableFlashcardList(ReadOnlyFlashcardList source) {
        flashcards.addAll(source.getFlashcardList().stream().map(JsonAdaptedFlashcard::new).collect(Collectors.toList()));
    }

    /**
     * Converts this flashcard list into the model's {@code FlashcardList} object.
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FlashcardList toModelType() throws IllegalValueException {
        FlashcardList flashcardList = new FlashcardList();
        for (JsonAdaptedFlashcard jsonAdaptedFlashcard : flashcards) {
            Flashcard flashcard = jsonAdaptedFlashcard.toModelType();
            if (flashcardList.hasFlashcard(flashcard)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FLASHCARD);
            }
            flashcardList.addFlashcard(flashcard);
        }
        return flashcardList;
    }

}
