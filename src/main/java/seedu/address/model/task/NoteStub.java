package seedu.address.model.task;

import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;

/**
 * Represents a stub for Note class.
 */
class NoteStub extends Note {
    /**
     * Constructs a new lecture note. Both fields must be present and non-null.
     *
     * @param title   The lecture note's title, which must be unique among all lecture notes.
     * @param content The lecture note's content (newlines are supported), which do not have to be unique.
     */
    NoteStub(Title title, Content content) {
        super(title, content);
    }
}
