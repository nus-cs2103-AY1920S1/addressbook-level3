package seedu.address.storage.note;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.note.Note;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.note.ReadOnlyNotesRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable notesRecord that is serializable to JSON format.
 */

@JsonRootName(value = "notesRecord")
public class JsonSerializableNotesRecord {

    public static final String MESSAGE_DUPLICATE_NOTE = "Notes list contains duplicate note(s).";

    private final List<JsonAdaptedNote> notes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableNotesRecord} with the given notes.
     */
    @JsonCreator
    public JsonSerializableNotesRecord(@JsonProperty("notes") List<JsonAdaptedNote> notes) {
        this.notes.addAll(notes);
    }

    /**
     * Converts a given {@code ReadOnlyNotesRecord} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNotesRecord}.
     */
    public JsonSerializableNotesRecord(ReadOnlyNotesRecord source) {
        notes.addAll(source.getNotesList().stream().map(JsonAdaptedNote::new).collect(Collectors.toList()));
    }

    /**
     * Converts this notes record into the model's {@code NotesRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public NotesRecord toModelType() throws IllegalValueException {
        NotesRecord notesRecord = new NotesRecord();
        for (JsonAdaptedNote jsonAdaptedNote : notes) {
            Note note = jsonAdaptedNote.toModelType();
            if (notesRecord.hasNote(note)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_NOTE);
            }
            notesRecord.addNote(note);
        }
        return notesRecord;
    }
}