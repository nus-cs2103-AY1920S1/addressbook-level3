package tagline.model.note;

import java.util.function.Predicate;

/**
 * Tests that a {@code Note}'s {@code NoteId} matches the note id given.
 */
public class NoteIdEqualsTargetIdPredicate implements Predicate<Note> {
    private final NoteId noteId;

    public NoteIdEqualsTargetIdPredicate(NoteId targetId) {
        noteId = targetId;
    }

    @Override
    public boolean test(Note note) {
        return note.getNoteId().equals(noteId);
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteIdEqualsTargetIdPredicate // instanceof handles null
                && noteId.equals(((NoteIdEqualsTargetIdPredicate) other).noteId)); //state check
    }
}
