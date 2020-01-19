package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.storage.Storage;

/**
 * Deletes an offline copy of a bookmark.
 */
public class DeleteCacheCommand extends Command {

    public static final String COMMAND_WORD = "cache-delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the offline copy of a bookmark.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Bookmark's offline copy successfully deleted!";
    public static final String MESSAGE_FAILURE =
            "No offline copy is available to delete!!";

    private Index index;

    /**
     * Instantiates a new DeleteCache Command.
     *
     * @param index the index of the bookmark whose offline copy should be deleted
     */
    public DeleteCacheCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);
        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        Bookmark bookmarkToDelete = lastShownList.get(index.getZeroBased());

        if (bookmarkToDelete.getCachedCopies().isEmpty()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        Bookmark updatedBookmark = new Bookmark(bookmarkToDelete.getName(), bookmarkToDelete.getUrl(),
                bookmarkToDelete.getRemark(), bookmarkToDelete.getFolder(), bookmarkToDelete.getTags(),
                Collections.emptyList());
        if (model.getObservableOfflineDocNameCurrentlyShowing().getValue().equals(updatedBookmark.getName().value)) {
            model.updateDocument(new OfflineDocument(""));
            model.setOfflineDocNameCurrentlyShowing(OfflineDocument.NAME_NO_DOCUMENT);
        }
        model.setBookmark(bookmarkToDelete, updatedBookmark);
        model.saveMark(MESSAGE_SUCCESS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCacheCommand // instanceof handles nulls
                && index.equals(((DeleteCacheCommand) other).index));
    }
}
