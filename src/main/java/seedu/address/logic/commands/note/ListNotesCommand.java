package seedu.address.logic.commands.note;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

public class ListNotesCommand extends Command {
    public static final String COMMAND_WORD = "listnote";

    public static final String MESSAGE_SUCCESS = "Listed all notes";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredNotesList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
