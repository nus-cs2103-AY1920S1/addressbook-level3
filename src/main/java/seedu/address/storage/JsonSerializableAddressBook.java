package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.flashcard.FlashCard;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_FLASHCARD = "Flashcards list contains duplicate flashCard(s).";

    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();
    private final List<JsonAdaptedDeadline> deadlines = new ArrayList<>();

    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("flashcards") List<JsonAdaptedFlashcard> flashcards,
                                       @JsonProperty("deadlines") List<JsonAdaptedDeadline> deadlines) {
        this.flashcards.addAll(flashcards);
        this.deadlines.addAll(deadlines);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
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
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedFlashcard jsonAdaptedFlashcard : flashcards) {
            FlashCard flashCard = jsonAdaptedFlashcard.toModelType();
            if (addressBook.hasFlashcard(flashCard)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FLASHCARD);
            }
            addressBook.addFlashcard(flashCard);
        }

        for (JsonAdaptedDeadline jsonAdaptedDeadline : deadlines) {
            Deadline deadline = jsonAdaptedDeadline.toModelType();
            addressBook.addDeadline(deadline);
        }
        return addressBook;
    }
}
