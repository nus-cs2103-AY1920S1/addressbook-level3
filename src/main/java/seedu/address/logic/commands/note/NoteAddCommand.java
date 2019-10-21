package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;

/**
 * Creates a new note to be added to the note list.
 */
public class NoteAddCommand extends NoteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new note\n"
            + "Parameters:\n"
            + "note/{Note Title}\n"
            + "desc/{Description}\n"
            + "Example: note note/Tuesday desc/Grade 6A prelim papers.\n";

    public static final String MESSAGE_SUCCESS = "New Note added: %1$s";
    public static final String MESSAGE_DUPLICATE_NOTE = "This note already exists in the notes recod";

    private final Note toAdd;

    /**
     * Creates a NoteAddCommand to add the specified {@code Note}
     *
     * @param note to set.
     */
    public NoteAddCommand(Note note) {
        requireNonNull(note);
        toAdd = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasNote(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTE);
        }

        model.addNote(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteAddCommand // instanceof handles nulls
                && toAdd.equals(((NoteAddCommand) other).toAdd));
    }
}
