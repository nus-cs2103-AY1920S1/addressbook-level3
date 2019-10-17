package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.mark.model.Model.PREDICATE_SHOW_ALL_BOOKMARKS;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.storage.Storage;

/**
 * Reverts the {@code model}'s Mark to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Successfully redo the undone action: %1$s";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        if (!model.canRedoMark()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        String record = model.redoMark();
        model.updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, record));
    }
}
