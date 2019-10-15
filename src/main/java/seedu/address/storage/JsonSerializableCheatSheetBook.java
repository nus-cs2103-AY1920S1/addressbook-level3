package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyCheatSheetBook;
import seedu.address.model.ReadOnlyFlashcardBook;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Flashcard;

/**
 * An Immutable CheatSheetBook that is serializable to JSON format.
 */
@JsonRootName(value = "cheatsheetbook")
class JsonSerializableCheatSheetBook {

    public static final String MESSAGE_DUPLICATE_CHEATSHEET = "Cheatsheet list contains duplicate cheatsheet(s).";

    private final List<JsonAdaptedCheatSheet> cheatSheets = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCheatSheetBook} with the given flashcards.
     */
    @JsonCreator
    public JsonSerializableCheatSheetBook(@JsonProperty("cheatSheets") List<JsonAdaptedCheatSheet> cheatSheets) {
        this.cheatSheets.addAll(cheatSheets);
    }

    /**
     * Converts a given {@code ReadOnlyCheatSheetBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCheatSheetBook}.
     */
    public JsonSerializableCheatSheetBook(ReadOnlyCheatSheetBook source) {
        cheatSheets.addAll(source.getCheatSheetList().stream()
                .map(JsonAdaptedCheatSheet::new).collect(Collectors.toList()));
    }

    /**
     * Converts this flashcard book into the model's {@code CheatSheetBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedCheatSheet jsonAdaptedCheatSheet : cheatSheets) {
            CheatSheet cheatSheet = jsonAdaptedCheatSheet.toModelType();
            if (addressBook.hasCheatSheet(cheatSheet)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CHEATSHEET);
            }
            addressBook.addCheatSheet(cheatSheet);
        }
        return addressBook;
    }

}
