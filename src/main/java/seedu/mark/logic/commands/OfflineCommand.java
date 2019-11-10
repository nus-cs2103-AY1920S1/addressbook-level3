package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.OfflineCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.CachedCopy;
import seedu.mark.storage.Storage;

/**
 * Opens a cached version of a bookmark.
 */
public class OfflineCommand extends Command {

    public static final String COMMAND_WORD = "offline";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the cached copy of the bookmark identified by the index used in the displayed bookmark list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_VERSION = "Could not find cached bookmark version %1$d";
    public static final String MESSAGE_SUCCESS = "Opening cached Bookmark: %1$s";
    public static final String MESSAGE_NO_CACHED_COPIES =
            "There are no cached copies yet. Use the cache command to do so first.";

    private final Index targetIndex;

    /**
     * Instantiates a new Offline command.
     *
     * @param targetIndex the target index
     */
    public OfflineCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);
        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        Bookmark bookmarkToOpen = lastShownList.get(targetIndex.getZeroBased());
        List<CachedCopy> cachedCopies = bookmarkToOpen.getCachedCopies();

        int version = cachedCopies.size() - 1; // TODO: implement choosing version
        if (version < 0) {
            throw new CommandException(MESSAGE_NO_CACHED_COPIES);
        }
        if (version >= cachedCopies.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_VERSION, version));
        }
        model.updateDocument(bookmarkToOpen.getCachedCopies().get(version).getAnnotations());
        model.setOfflineDocNameCurrentlyShowing(bookmarkToOpen.getName().value);
        model.saveMark(String.format(MESSAGE_SUCCESS, bookmarkToOpen));

        return new OfflineCommandResult(String.format(MESSAGE_SUCCESS, bookmarkToOpen));
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OfflineCommand)) {
            return false;
        }

        OfflineCommand otherOfflineCommand = (OfflineCommand) other;
        return targetIndex.equals(otherOfflineCommand.targetIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex);
    }
}
