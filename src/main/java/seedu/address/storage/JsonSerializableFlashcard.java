package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyStudyBuddyPro;
import seedu.address.model.StudyBuddyPro;
import seedu.address.model.flashcard.Flashcard;

/**
 * An Immutable Flashcards class that is serializable to JSON format.
 */
@JsonRootName(value = "flashcards")
class JsonSerializableFlashcard {

    public static final String MESSAGE_DUPLICATE_FLASHCARD = "Flashcard list contains duplicate flashcard(s).";

    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFlashcard} with the given flashcards.
     */
    @JsonCreator
    public JsonSerializableFlashcard(@JsonProperty("flashcards") List<JsonAdaptedFlashcard> flashcards) {
        this.flashcards.addAll(flashcards);
    }

    /**
     * Converts a given {@code ReadOnlyFlashcardBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFlashcard}.
     */
    public JsonSerializableFlashcard(ReadOnlyStudyBuddyPro source) {
        flashcards.addAll(source.getFlashcardList().stream()
                .map(JsonAdaptedFlashcard::new).collect(Collectors.toList()));
    }

    /**
     * Converts this flashcard book into the model's {@code Flashcard} object.
     * @param studyBuddyPro the addressBook flashcards should be written to
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StudyBuddyPro toModelType(StudyBuddyPro studyBuddyPro) throws IllegalValueException {
        for (JsonAdaptedFlashcard jsonAdaptedFlashcard : flashcards) {
            Flashcard flashcard = jsonAdaptedFlashcard.toModelType();
            if (studyBuddyPro.hasFlashcard(flashcard)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FLASHCARD);
            }
            studyBuddyPro.addFlashcard(flashcard);
        }
        return studyBuddyPro;
    }

}
