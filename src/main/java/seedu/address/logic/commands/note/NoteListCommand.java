package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.ReadOnlyNotesRecord;

/**
 * Lists the summary of notes in note list.
 */
public class NoteListCommand extends NoteCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " list: List of notes";
    public static final String MESSAGE_SUCCESS = "Listed all notes";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredNotesList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(generateSuccessMessage(model.getNotesRecord()));
    }

    /**
     * Generates a command execution success message.
     * @param notesRecord The relevant notesRecord from the model.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage(ReadOnlyNotesRecord notesRecord) {
        return "This is the list of notes: " + "\n"
                + notesRecord;
    }
}
