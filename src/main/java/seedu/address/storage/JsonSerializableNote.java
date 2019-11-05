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
import seedu.address.model.note.Note;

/**
 * An Immutable Notes class that is serializable to JSON format.
 */
@JsonRootName(value = "notes")
class JsonSerializableNote {

    public static final String MESSAGE_DUPLICATE_NOTE = "Note list contains duplicate note(s).";

    private final List<JsonAdaptedNote> notes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableNote} with the given notes.
     */
    @JsonCreator
    public JsonSerializableNote(@JsonProperty("notes") List<JsonAdaptedNote> notes) {
        this.notes.addAll(notes);
    }

    /**
     * Converts a given {@code ReadOnlyNoteBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNote}.
     */
    public JsonSerializableNote(ReadOnlyStudyBuddyPro source) {
        notes.addAll(source.getNoteList().stream()
                .map(JsonAdaptedNote::new).collect(Collectors.toList()));
    }

    /**
     * Converts this note book into the model's {@code Note} object.
     * @param studyBuddyPro the addressBook notes should be written to
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StudyBuddyPro toModelType(StudyBuddyPro studyBuddyPro) throws IllegalValueException {
        for (JsonAdaptedNote jsonAdaptedNote : notes) {
            Note note = jsonAdaptedNote.toModelType();
            if (studyBuddyPro.hasNote(note)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_NOTE);
            }
            studyBuddyPro.addNote(note);
        }
        return studyBuddyPro;
    }

}
