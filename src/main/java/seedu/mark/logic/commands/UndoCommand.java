package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.model.Model.PREDICATE_SHOW_ALL_BOOKMARKS;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Model;

/**
 * Reverts the {@code model}'s mark to its previous state.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoMark()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoMark();
        model.updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
