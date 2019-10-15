package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;

/**
 * Adds a person to the address book.
 */
public class AddNoteCommand extends Command {

    public static final String COMMAND_WORD = "add_note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note to the list of notes. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_CONTENT + "CONTENT "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Pipelining Definition "
            + PREFIX_CONTENT + "Pipelining is a process where a processor runs multiple instructions simultaneously, "
            + "each at different stages of the processor. "
            + PREFIX_TAG + "CS2100 "
            + PREFIX_TAG + "Pipelining";

    public static final String MESSAGE_SUCCESS = "New note added: %1$s";
    public static final String MESSAGE_DUPLICATE_NOTE = "This note title already exists in FiveNotes.";

    private final Note toAdd;

    /**
     * Creates an AddNoteCommand to add the specified {@code Note}
     */
    public AddNoteCommand(Note note) {
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
                || (other instanceof AddNoteCommand // instanceof handles nulls
                && toAdd.equals(((AddNoteCommand) other).toAdd));
    }
}
