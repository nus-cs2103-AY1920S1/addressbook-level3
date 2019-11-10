package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;



/**
 * Adds a person to the address book.
 */
public class AddNotesCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note to the notebook. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_TAG + "TAG "
            + PREFIX_CONTENT + "CONTENT";

    public static final String MESSAGE_SUCCESS = "New note added: %1$s";
    public static final String MESSAGE_DUPLICATE_NOTE = "A note with this title already exists in the note book";

    private final Note toAdd;
    private final String command;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddNotesCommand(Note note, String commandArgs) {
        requireNonNull(note);
        requireNonNull(commandArgs);
        toAdd = note;
        this.command = COMMAND_WORD + " " + commandArgs;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasNote(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTE);
        }
        model.commitNote(command);
        model.addNote(toAdd);
        model.sortNoteBook();
        model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddNotesCommand // instanceof handles nulls
                && toAdd.equals(((AddNotesCommand) other).toAdd));
    }
}
