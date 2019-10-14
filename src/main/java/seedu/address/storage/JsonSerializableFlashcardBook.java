package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyFlashcardBook;
import seedu.address.model.flashcard.Flashcard;

/**
 * An Immutable FlashcardBook that is serializable to JSON format.
 */
@JsonRootName(value = "flashcardbook")
class JsonSerializableFlashcardBook {

    public static final String MESSAGE_DUPLICATE_FLASHCARD = "Flashcard list contains duplicate flashcard(s).";

    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFlashcardBook} with the given flashcards.
     */
    @JsonCreator
    public JsonSerializableFlashcardBook(@JsonProperty("flashcards") List<JsonAdaptedFlashcard> flashcards) {
        this.flashcards.addAll(flashcards);
    }

    /**
     * Converts a given {@code ReadOnlyFlashcardBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFlashcardBook}.
     */
    public JsonSerializableFlashcardBook(ReadOnlyFlashcardBook source) {
        flashcards.addAll(source.getFlashcardList().stream().map(JsonAdaptedFlashcard::new).collect(Collectors.toList()));
    }

    /**
     * Converts this flashcard book into the model's {@code FlashcardBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedFlashcard jsonAdaptedFlashcard : flashcards) {
            Flashcard flashcard = jsonAdaptedFlashcard.toModelType();
            if (addressBook.hasFlashcard(flashcard)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FLASHCARD);
            }
            addressBook.addFlashcard(flashcard);
        }
        return addressBook;
    }

}
