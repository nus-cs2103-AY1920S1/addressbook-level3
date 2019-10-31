// @@author shiweing
package tagline.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_CONTENT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TAG;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TITLE;
import static tagline.model.note.NoteModel.PREDICATE_SHOW_ALL_NOTES;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.note.Note;

/**
 * Creates a new note.
 */
public class CreateNoteCommand extends NoteCommand {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_KEY + " " + COMMAND_WORD + ": Creates a new note.\n"
            + "Parameters: "
            + PREFIX_CONTENT + "CONTENT "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_TAG + "TAG...]\n"
            + "Example: " + COMMAND_KEY + " " + COMMAND_WORD + " "
            + PREFIX_TITLE + "CS2103T TP "
            + PREFIX_CONTENT + "CS2103T meeting on Wednesday "
            + PREFIX_TAG + "cs2103t "
            + PREFIX_TAG + "meeting";

    public static final String MESSAGE_SUCCESS = "New note added: %1$s";
    public static final String MESSAGE_DUPLICATE_NOTE = "This note already exists.";

    private final Note toCreate;

    /**
     * Creates a CreateNoteCommand to add the specified {@code Note}
     */
    public CreateNoteCommand(Note note) {
        requireNonNull(note);
        toCreate = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasNote(toCreate)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTE);
        }

        model.addNote(toCreate);
        model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toCreate), ViewType.NOTE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateNoteCommand // instanceof handles nulls
                && toCreate.getNoteId().equals(((CreateNoteCommand) other).toCreate.getNoteId())
                && toCreate.getTitle().equals(((CreateNoteCommand) other).toCreate.getTitle())
                && toCreate.getContent().equals(((CreateNoteCommand) other).toCreate.getContent())
                && toCreate.getTags().equals(((CreateNoteCommand) other).toCreate.getTags()));
    }
}
