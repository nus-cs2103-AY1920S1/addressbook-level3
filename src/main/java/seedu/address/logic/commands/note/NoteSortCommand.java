package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;

/**
 * Sorts the note list into their priorities.
 */
public class NoteSortCommand extends NoteCommand {

    public static final String MESSAGE_USAGE = "Example: "
            + COMMAND_WORD + " sort\n"
            + "The sort command cannot have additional fields.";

    public static final String MESSAGE_SUCCESS = "Note sorted by Priority";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortNotesRecord(new Note.NoteComparator());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof NoteSortCommand;
    }
}
