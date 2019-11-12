package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.NoteCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;

/**
 * Adds a person to the address book.
 */
public class AddNoteCommand extends Command {

    public static final String COMMAND_WORD = ADD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note to the list of notes.\n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_CONTENT + "CONTENT "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Pipelining Definition "
            + PREFIX_CONTENT + "Pipelining is a process where a processor runs multiple instructions simultaneously, "
            + "each at different stages of the processor. "
            + PREFIX_TAG + "CS2100 "
            + PREFIX_TAG + "Pipelining"
            + "\nOptional Parameters: (for Intra-Note tagging within CONTENT): "
            + PREFIX_NOTE_FRAGMENT_START + " "
            + PREFIX_NOTE_FRAGMENT_CONTENT + "INTRA_CONTENT "
            + PREFIX_NOTE_FRAGMENT_TAG + "INTRA_TAG "
            + "[" + PREFIX_NOTE_FRAGMENT_TAG + "ADDITIONAL_INTRA_TAG]... "
            + PREFIX_NOTE_FRAGMENT_END
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Pipelining Definition "
            + PREFIX_CONTENT + "Pipelining is a process where /* C/a processor runs multiple instructions "
            + "simultaneously TAG/important TAG/midterm */, each at different stages of the processor. "
            + PREFIX_TAG + "CS2100 "
            + PREFIX_TAG + "Pipelining";

    public static final String MESSAGE_SUCCESS = "New note added: %1$s";
    public static final String MESSAGE_DUPLICATE_NOTE = "This note title already exists in Notes.";
    private static final Logger logger = LogsCenter.getLogger(AddNoteCommand.class);

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

        int noteListSize = model.getFilteredNoteList().size();
        model.addNote(toAdd);
        assert (model.getFilteredNoteList().size() - noteListSize == 1);
        logger.info("Current list size: " + model.getFilteredNoteList().size());

        return new NoteCommandResult(String.format(MESSAGE_SUCCESS, toAdd.toStringWithNoteFragments()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddNoteCommand // instanceof handles nulls
                && toAdd.equals(((AddNoteCommand) other).toAdd));
    }
}
