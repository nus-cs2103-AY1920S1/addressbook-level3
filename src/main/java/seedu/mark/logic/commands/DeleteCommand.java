package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Bookmark;

/**
 * Deletes a bookmark identified using its displayed index from Mark.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the bookmark identified by the index number used in the displayed bookmark list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BOOKMARK_SUCCESS = "Deleted Bookmark: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        Bookmark bookmarkToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBookmark(bookmarkToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_BOOKMARK_SUCCESS, bookmarkToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
