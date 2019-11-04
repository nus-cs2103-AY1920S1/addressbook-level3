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
import seedu.address.model.cheatsheet.CheatSheet;

/**
 * An Immutable CheatSheet that is serializable to JSON format.
 */
@JsonRootName(value = "cheatsheet")
class JsonSerializableCheatSheet {

    public static final String MESSAGE_DUPLICATE_CHEATSHEET = "Cheatsheet list contains duplicate cheatsheet(s).";

    private final List<JsonAdaptedCheatSheet> cheatSheets = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCheatSheet} with the given cheatsheets.
     */
    @JsonCreator
    public JsonSerializableCheatSheet(@JsonProperty("cheatSheets") List<JsonAdaptedCheatSheet> cheatSheets) {
        this.cheatSheets.addAll(cheatSheets);
    }

    /**
     * Converts a given {@code ReadOnlyCheatSheet} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCheatSheet}.
     */
    public JsonSerializableCheatSheet(ReadOnlyStudyBuddyPro source) {
        cheatSheets.addAll(source.getCheatSheetList().stream()
                .map(JsonAdaptedCheatSheet::new).collect(Collectors.toList()));
    }

    /**
     * Converts this flashcard book into the model's {@code CheatSheet} object.
     * @param studyBuddyPro the addressBook flashcards should be written to
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StudyBuddyPro toModelType(StudyBuddyPro studyBuddyPro) throws IllegalValueException {
        for (JsonAdaptedCheatSheet jsonAdaptedCheatSheet : cheatSheets) {
            CheatSheet cheatSheet = jsonAdaptedCheatSheet.toModelType();
            if (studyBuddyPro.hasCheatSheet(cheatSheet)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CHEATSHEET);
            }
            studyBuddyPro.addCheatSheet(cheatSheet);
        }
        return studyBuddyPro;
    }

}
