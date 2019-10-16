package tagline.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.note.Note;
import tagline.model.note.NoteBook;
import tagline.model.note.ReadOnlyNoteBook;

/**
 * An Immutable NoteBook that is serializable to JSON format.
 */
@JsonRootName(value = "notebook")
class JsonSerializableNoteBook {

    public static final String MESSAGE_DUPLICATE_NOTE = "Notes list contains duplicate note(s).";

    private final List<JsonAdaptedNote> notes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableNoteBook} with the given notes.
     */
    @JsonCreator
    public JsonSerializableNoteBook(@JsonProperty("notes") List<JsonAdaptedNote> notes) {
        this.notes.addAll(notes);
    }

    /**
     * Converts a given {@code ReadOnlyNoteBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNoteBook}.
     */
    public JsonSerializableNoteBook(ReadOnlyNoteBook source) {
        notes.addAll(source.getNoteList().stream().map(JsonAdaptedNote::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code NoteBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public NoteBook toModelType() throws IllegalValueException {
        NoteBook noteBook = new NoteBook();
        for (JsonAdaptedNote jsonAdaptedNote : notes) {
            Note note = jsonAdaptedNote.toModelType();
            if (noteBook.hasNote(note)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_NOTE);
            }
            noteBook.addNote(note);
        }
        return noteBook;
    }

}
