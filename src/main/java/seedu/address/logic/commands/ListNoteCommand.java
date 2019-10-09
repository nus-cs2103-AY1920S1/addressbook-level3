package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import seedu.address.model.Model;

/**
 * Lists all lecture notes.
 */
public class ListNoteCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all lecture notes";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
