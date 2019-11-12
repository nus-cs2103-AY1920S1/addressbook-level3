package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.VersionedNoteBook;


/**
 * Clears the address book.
 */
public class ClearNoteCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Note book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.commitNoteBook("clear");
        model.setNoteBook(new VersionedNoteBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
