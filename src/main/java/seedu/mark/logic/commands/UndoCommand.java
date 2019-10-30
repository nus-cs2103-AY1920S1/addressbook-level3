package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.mark.model.Model.PREDICATE_SHOW_ALL_BOOKMARKS;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.storage.Storage;

/**
 * Reverts the {@code model}'s Mark to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Action successfully undone: \"%1$s\"";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        if (!model.canUndoMark()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        String record = model.undoMark();
        model.updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);
        model.updateCurrentAnnotation();
        return new CommandResult(String.format(MESSAGE_SUCCESS, record));
    }
}
