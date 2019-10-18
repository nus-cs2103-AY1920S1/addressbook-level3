package seedu.address.storage.note;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.note.Note;

/**
 * Jackson-friendly version of {@link Note}.
 */
public class JsonAdaptedNote {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Note's %s field is missing!";

    private final String note;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedNote} with the given note details.
     */
    @JsonCreator
    public JsonAdaptedNote(@JsonProperty("note") String note, @JsonProperty("description") String description) {
        this.note = note;
        this.description = description;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedNote(Note source) {
        note = source.getNote();
        description = source.getDescription();
    }

    /**
     * Converts this Jackson-friendly adapted note object into the model's {@code Note} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted note.
     */
    public Note toModelType() throws IllegalValueException {
        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "note/{note title}"));
        }
        return new Note(note, description);
    }
}
