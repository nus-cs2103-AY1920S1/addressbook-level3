package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.KeyboardFlashCards;
import seedu.address.model.ReadOnlyKeyboardFlashCards;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.flashcard.FlashCard;

/**
 * An Immutable KeyboardFlashCards that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableKeyboardFlashCards {

    public static final String MESSAGE_DUPLICATE_FLASHCARD = "Flashcards list contains duplicate flashCard(s).";
    public static final String MESSAGE_DUPLICATE_DEADLINE = "Deadlines list contains duplicate Deadline(s).";

    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();
    private final List<JsonAdaptedDeadline> deadlines = new ArrayList<>();

    @JsonCreator
    public JsonSerializableKeyboardFlashCards(@JsonProperty("flashcards") List<JsonAdaptedFlashcard> flashcards,
                                              @JsonProperty("deadlines") List<JsonAdaptedDeadline> deadlines) {
        this.flashcards.addAll(flashcards);
        this.deadlines.addAll(deadlines);
    }

    /**
     * Converts a given {@code ReadOnlyKeyboardFlashCards} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableKeyboardFlashCards}.
     */
    public JsonSerializableKeyboardFlashCards(ReadOnlyKeyboardFlashCards source) {
        flashcards.addAll(
                source.getFlashcardList()
                        .stream()
                        .map(JsonAdaptedFlashcard::new)
                        .collect(Collectors.toList()));
        deadlines.addAll(
                source.getDeadlineList()
                .stream()
                .map(JsonAdaptedDeadline::new)
                .collect(Collectors.toList()));
    }


    /**
     * Converts this address book into the model's {@code KeyboardFlashCards} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public KeyboardFlashCards toModelType() throws IllegalValueException {
        KeyboardFlashCards keyboardFlashCards = new KeyboardFlashCards();
        for (JsonAdaptedFlashcard jsonAdaptedFlashcard : flashcards) {
            FlashCard flashCard = jsonAdaptedFlashcard.toModelType();
            if (keyboardFlashCards.hasFlashcard(flashCard)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FLASHCARD);
            }
            keyboardFlashCards.addFlashcard(flashCard);
        }

        for (JsonAdaptedDeadline jsonAdaptedDeadline : deadlines) {
            Deadline deadline = jsonAdaptedDeadline.toModelType();
            if (keyboardFlashCards.hasDeadline(deadline)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DEADLINE);
            }
            keyboardFlashCards.addDeadline(deadline);
        }
        return keyboardFlashCards;
    }
}
