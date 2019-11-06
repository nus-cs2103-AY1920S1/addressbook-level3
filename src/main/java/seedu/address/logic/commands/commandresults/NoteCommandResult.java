package seedu.address.logic.commands.commandresults;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.note.Note;

/**
 * Represents the result of a command execution.
 */
public class NoteCommandResult extends CommandResult {

    private final String feedbackToUser;

    /** Note to display (if any) */
    private final Optional<Note> note;

    /**
     * Constructs a {@code NoteCommandResult} with the specified fields.
     */
    public NoteCommandResult(String feedbackToUser, Optional<Note> note) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.note = note;
    }

    /**
     * Constructs a {@code NoteCommandResult} with the specified fields.
     */
    public NoteCommandResult(String feedbackToUser) {
        this(feedbackToUser, Optional.empty());
    }

    @Override
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Optional<Note> getNote() {
        return note;
    }

    @Override
    public boolean isNoteCommandResult() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommandResult)) {
            return false;
        }

        NoteCommandResult otherNoteCommandResult = (NoteCommandResult) other;
        return feedbackToUser.equals(otherNoteCommandResult.feedbackToUser)
                && note.equals(otherNoteCommandResult.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, note);
    }

}
