package seedu.address.storage.note;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.note.Note;
import seedu.address.model.note.Priority;

/**
 * Jackson-friendly version of {@link Note}.
 */
public class JsonAdaptedNote {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Note has illegal format. Please Refer to User Guide.";

    private final String note;
    private final String description;
    private final String priority;

    /**
     * Constructs a {@code JsonAdaptedNote} with the given note details.
     */
    @JsonCreator
    public JsonAdaptedNote(@JsonProperty("note") String note, @JsonProperty("description") String description,
                           @JsonProperty("priority") String priority) {
        this.note = note;
        this.description = description;
        this.priority = priority;
    }

    /**
     * Converts a given {@code source} into this class for Jackson use.
     */
    public JsonAdaptedNote(Note source) {
        note = source.getNote();
        description = source.getDescription();
        priority = source.getPriority().toString();
    }

    /**
     * Converts this Jackson-friendly adapted note object into the model's {@code Note} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted note.
     */
    public Note toModelType() throws IllegalValueException {
        if (note == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        if (priority.isEmpty()) {
            return new Note(note, description, Priority.UNMARKED);
        }
        return new Note(note, description, Priority.getPriority(priority));
    }
}
